import requests
import sys

try:
    session = requests.Session()
    print("Testing Registration...")
    r1 = session.post("http://localhost:8080/addingUser", data={
        "uname": "Owner", "uemail": "owner@test.com", "upassword": "pass", "unumber": "123"
    }, allow_redirects=False)
    print("Register Status:", r1.status_code)

    print("Testing Login...")
    r2 = session.get("http://localhost:8080/userlogin", params={
        "userEmail": "owner@test.com", "userPassword": "pass"
    }, allow_redirects=False)
    print("Login Status:", r2.status_code)

    print("Testing Add Equipment...")
    r3 = session.post("http://localhost:8080/addingEquipment", data={
        "pname": "Tractor", "pprice": "1000", "pdescription": "Testing"
    }, allow_redirects=False)
    print("Add Equipment Status:", r3.status_code)
except Exception as e:
    print("Error:", e)
