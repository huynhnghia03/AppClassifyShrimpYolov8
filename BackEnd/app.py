from flask import Flask, request, jsonify
from flask_jwt_extended import JWTManager, create_access_token, jwt_required, get_jwt_identity
from werkzeug.security import generate_password_hash, check_password_hash
from flask_cors import CORS
import os
from werkzeug.utils import secure_filename
from flask_mysqldb import MySQL
from datetime import datetime, timedelta
from dotenv import load_dotenv
import my_YoloV8
import cv2
import json
import random
import imghdr

# from random import random
# Khởi tạo Flask Server Backend
load_dotenv()
app = Flask(__name__)
ALLOWED_EXTENSIONS = set(['png', 'jpg', 'jpeg', 'gif', 'mp4','webp'])
# Apply Flask CORS
CORS(app)
app.config['CORS_HEADERS'] = 'Content-Type'
app.config['UPLOAD_FOLDER'] = "static"

app.config['MYSQL_HOST'] = 'localhost'
app.config['MYSQL_USER'] = 'root'
app.config['MYSQL_PASSWORD'] = '12345678'
app.config['MYSQL_DB'] = 'yolov8shrimp'
mysql = MySQL(app)

model = my_YoloV8.YOLOv8_ObjectCounter(model_file="best1686.pt")


app.secret_key = os.environ.get("FLASK_SECRET")
app.config['JWT_SECRET_KEY'] = 'your_jwt_secret_key'
app.config['JWT_ACCESS_TOKEN_EXPIRES'] = timedelta(hours=24)
jwt = JWTManager(app)


@app.route("/login", methods=["POST"])
def login():
        email = request.json.get('email')
        pwd = request.json.get('password')
        cur = mysql.connection.cursor()
        cur.execute(f"select * from user where email = '{email}'")
        users = cur.fetchone()
        cur.close()
        # check_password_hash(users[3], pwd)
        if users and  check_password_hash(users[3], pwd):
            print("ok")
            access_token = create_access_token(identity=users[0])
            return jsonify({"email":users[0],"auth":True,"password":"","username":users[1], "avatar":users[2], "admin":users[4],"date":users[5],"access_token":"Bearer "+access_token})
        else:
            return jsonify({"auth":False})

@app.route("/register", methods=["POST"])
def register():
        print("ok")
        print(request.json.get("email"))
        email = request.json.get("email")
        password = generate_password_hash(request.json.get('password'))
        date = datetime.now()
        cur = mysql.connection.cursor()
        cur.execute(f"SELECT * FROM user WHERE email = '{email}'")
        existing_user = cur.fetchone()
        cur.close()
        if existing_user:
            return jsonify({"auth":False})
        else:
            cur = mysql.connection.cursor()
            user_name = email.split('@')[0]
            print(user_name)
            cur.execute(f"INSERT INTO user (email,username, avatar, password, admin, date) VALUES ('{email}','{user_name}','{''}','{password}','false','{date.date()}')")
            mysql.connection.commit()
            return jsonify({"auth":True})

@app.route("/getAllUsers")
def getUsers():
    cur = mysql.connection.cursor()
    cur.execute(f"SELECT * FROM user")
    users = cur.fetchall()
    cur.execute(f"SELECT * FROM history")
    histories = cur.fetchall()
    dataUserArr = list()
    count = set()
    for record in histories:
        count.add(record[5])
    for record in users:
        dataUserDicts = dict()
        dataUserDicts["username"] = record[1]
        dataUserDicts["email"]=record[0]
        dataUserDicts["avatar"]=record[2]
        dataUserDicts["admin"]=record[4]
        dataUserDicts["date"] = record[5]
        # print(record[0] in count)
        if(record[0] in count):
            dataUserDicts["detected"]=True
        else:
            dataUserDicts["detected"] = False
        dataUserArr.append(dataUserDicts)
    cur.close()
    if users:
        return jsonify({"dataUsers": dataUserArr, "totalUsers":len(users),'totalHistory':count.__len__()})
    else:
        return jsonify({"exists": False})

@app.route("/detailUser/<email>")
def detailUser(email):
    cur = mysql.connection.cursor()
    cur.execute(f"SELECT * FROM user where email='{email}'")
    user = cur.fetchone()
    cur.execute(f"SELECT * FROM history where email='{email}'")
    histories = cur.fetchall()
    print(histories)
    dataUser = {
        "email": user[0],
        "username": user[1],
        "avatar": user[2],
        "admin": user[4],
        "date":user[5]
    }
    counters = {
         'total': 0,
            'medium': 0,
            'big': 0,
            'small': 0,
        "sumImgs": len(histories)
    }
    countShrimp(histories, counters)
    # print({"sumImgs":len(histories),"total" : counters["total_shrimp"],"big" : counters["total_big_shrimp"],"medium":counters["total_medium_shrimp"],"small":counters["total_small_shrimp"]})
    cur.close()
    if user:
        return jsonify({"dataUser": dataUser,"datas":counters,"dataHistories":histories})
    else:
        return jsonify({"exists": False})

@app.route("/history")
@jwt_required()
def history():
    current_user = get_jwt_identity()
    if current_user:
        cur = mysql.connection.cursor()
        cur.execute(f"SELECT * FROM history where email='{current_user}'")
        Executed_DATA = cur.fetchall()
        counters = {
            'total': 0,
            'medium': 0,
            'big': 0,
            'small': 0
        }
        countShrimp(Executed_DATA,counters)
        # print({"auth":True, "datas": Executed_DATA, "total" : total_shrimp,"big" : total_big_shrimp,"medium":total_medium_shrimp,"small":total_small_shrimp})
        return jsonify({"auth":True, "datas": Executed_DATA, "total" : counters["total"],"big" : counters["big"],"medium":counters["medium"],"small":counters["small"]})
 
    else:
        return jsonify({"auth":False})

@app.route('/delete_data', methods=['POST'])
def delete_data():
    id = request.json.get("id")
    print(id)
    CS = mysql.connection.cursor()
    try:
        CS.execute(f"""DELETE FROM history WHERE id = '{id}'""")
        mysql.connection.commit()
        CS.close()
        return jsonify({'success': True})
    except Exception as e:
        mysql.connection.rollback()
        CS.close()
        return jsonify({'success': False, 'error': str(e)})

@app.route('/classify', methods=['POST'])
@jwt_required()
def upload_file():
    current_user = get_jwt_identity()
    print(current_user)
    file = request.files.getlist('File')[0]

    if file:
        file_name = ""
        results_dict = {
            "SumShrimp":0,
            "BigShrimp":0,
            "MediumShrimp":0,
            "SmallShrimp":0,
        }
        is_video = False
        if imghdr.what(file) and file and allowed_file(file.filename):
                filename = secure_filename(file.filename)
                path_save = os.path.join(app.config['UPLOAD_FOLDER'] + "/image/img_process/", filename)
                file.save(path_save)
                frame = cv2.imread(path_save)
                results = model.predict_img(frame)
                result_img = model.custom_display(colors=color())
                msg = 'File predict successfully'
                print(path_save)
                # print(file_names)
                if len(results) > 0:
                    dictObject, save_name = model.count_object(results, app.config['UPLOAD_FOLDER'], result_img)
                    if(dictObject.__len__()>0):
                        results_dict.update(dictObject)

                    file_name="/static/yolov8/"+save_name
                    print(file_name)
                else:
                    file_name="/"+path_save
        else:
                is_video = True
                path_save = os.path.join(app.config['UPLOAD_FOLDER'] + "/image/img_process/", file.filename)
                file.save(path_save)
                msg = 'File predict successfully'
                file_name=path_save
        if(is_video):
            # generate(, CAP_DSHOWN=None, colors=color())
            return jsonify(
                {"video": True,
                 'success': True,
                 "file":file_name})
        else:
            cur = mysql.connection.cursor()
            filtered_dict = {k: v for k, v in results_dict.items() if k != 'SumShrimp'}
            key = json.dumps(filtered_dict)
            current_time = datetime.now().date()
            cur = mysql.connection.cursor()
            sumShrimp = results_dict['SumShrimp']
            email = current_user

            cur.execute(f"""INSERT INTO history (shrimp_image,shrimp_kind, shrimp_total, c_time, email) 
                                                   VALUES ('{file_name}','{key}','{sumShrimp}','{current_time}','{email}')""")
            mysql.connection.commit()
            cur.close()
            print(results_dict)
            newObjectDataShrimp={
                "total":results_dict["SumShrimp"],
                "big":results_dict["BigShrimp"],
                "medium":results_dict["MediumShrimp"],
                "small":results_dict["SmallShrimp"],

            }
            return jsonify(
                {
"msg":msg,
                 "Filename": file_name,
                 "Info": newObjectDataShrimp,
                "date":current_time,
                 "video": False,
                 'success': True, })
    else:
        return {"Filename": "",
                 "Info": "",
                 "video": False,
                 'success': False,}

@app.route("/change-password", methods=["POST"])
@jwt_required()
def change_password():
    email = get_jwt_identity()
    if email:
        current_pwd = request.json.get('oldpas')
        new_pwd = request.json.get('newpass')
        cur = mysql.connection.cursor()
        cur.execute(f"SELECT password FROM user WHERE email = '{email}'")
        user_data = cur.fetchone()
        print(check_password_hash(user_data[0], current_pwd))
        if user_data and check_password_hash(user_data[0], current_pwd):
            cur.execute(f"UPDATE user SET password = '{generate_password_hash(new_pwd)}' WHERE email = '{email}'")
            mysql.connection.commit()
            cur.close()
            return jsonify({'success': True})
        else:
            return jsonify({'success': False,"error":"Current password is incorrect"})
    else:
        return jsonify({'success': False,"error":"Cant find user"})
    
@app.route("/changeUsername", methods=["POST"])
def change_username():
    if request.form['email']:
        current_username = request.form['username']
        email = request.form['email']
        cur = mysql.connection.cursor()
        cur.execute(f"SELECT * FROM user WHERE email = '{email}'")
        user_data = cur.fetchone()
        path_save = user_data[2]
        if request.files.getlist('File'):
            current_avatar = request.files.getlist('File')[0]
            filename = secure_filename(current_avatar.filename)
            if(filename):
                path_save = os.path.join(app.config['UPLOAD_FOLDER'] + "/upload/users/", filename)
                current_avatar.save(path_save)
        if user_data:
            cur.execute(f"UPDATE user SET username = '{current_username}', avatar='/{path_save}' WHERE email = '{email}'")
            mysql.connection.commit()
            cur.execute(f"SELECT * FROM user WHERE email = '{email}'")
            Executed_DATA= cur.fetchone()
            print(Executed_DATA)
            cur.close()
            return jsonify({"email": Executed_DATA[0], "auth":True,"password":"","username":Executed_DATA[1], "avatar":Executed_DATA[2], "admin":Executed_DATA[4],"date":Executed_DATA[5],"access_token":"Bearer "+create_access_token(identity=Executed_DATA[0])})
        else:
            return jsonify({'success': True, 'image_path': "incorrect", "Info": {}})
    else:
        return jsonify({'success': False})

def countShrimp(Executed_DATA,counters):
    if (Executed_DATA):
        print("data1")
        for record in Executed_DATA:
            # tong tom
            shrimp_data = record[3]  # Assume shrimp data is always at index 2
            counters["total"] += shrimp_data

            shrimp_datakind = record[2]  # Giả sử dữ liệu của tôm luôn nằm ở chỉ mục 2
            shrimp_data_dict = json.loads(shrimp_datakind)  # Chuyển đổi chuỗi JSON thành từ điển
            shrimp_total_medium = shrimp_data_dict.get("MediumShrimp", 0)
            shrimp_total_big = shrimp_data_dict.get("BigShrimp", 0)
            shrimp_total_small = shrimp_data_dict.get("SmallShrimp", 0)

            counters["medium"] += shrimp_total_medium
            counters["big"] += shrimp_total_big
            counters["small"] += shrimp_total_small

def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS
def color():
    colors = []
    for _ in range(80):
        rand_tuple = (random.randint(50, 255), random.randint(50, 255), random.randint(50, 255))
        colors.append(rand_tuple)
    return colors
def random_color():
    return tuple(random.randint(0, 255) for _ in range(3))
if __name__ == '__main__':
    app.run(host="0.0.0.0", port=os.environ.get(
        "FLASK_PORT"), debug=True)

