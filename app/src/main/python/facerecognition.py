

def main(imageFile):


    print("hello")


    # noinspection PyInterpreter
    import face_recognition

    import mysql.connector


    mydb = mysql.connector.connect(
        host="10.167.122.103",
        port = "3308",
        user="username",
        passwd="password",
        db = "Project"
    )

    mycursor = mydb.cursor()

    mycursor.execute("SELECT FName, LName FROM Celebrities")

    for x in mycursor:
        print(x)




    print("2")


