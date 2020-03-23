

def main(encode):




    import face_recognition

    import mysql.connector

    import os



    conn = mysql.connector.connect(
        host="10.167.120.26",
        port = "3308",
        user="username",
        passwd="password",
        db = "Project"
    )

    #Getting all the names of all the celebrites in my database


    cursor=conn.cursor()
    print("1")


    print("2")
    sql = "INSERT INTO unknown (unknown_picture) VALUES (%s)"
    print("3")
    data = ("8", "hi", "bye", "6")
    cursor.execute(sql, (encode, ) )
    print("4")

    conn.commit()


    cursor.close()
    conn.close()



