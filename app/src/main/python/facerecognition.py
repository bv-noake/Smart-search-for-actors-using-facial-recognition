

def main(imageFile):


    print("hello")

    import face_recognition

    import mysql.connector

    import os



    known_face_names = []
    known_face_encodings = []




    conn = mysql.connector.connect(
        host="10.167.126.102",
        port = "3308",
        user="username",
        passwd="password",
        db = "Project"
    )

    #Getting all the names of all the celebrites in my database

    names = conn.cursor()

    names.execute("SELECT FName, LName FROM Celebrities")
    for x in names:
        known_face_names.append(x)

    print(known_face_names)

    names.close()

    #Getting all of the stored pictures of celebrities in my database

    #images = mydb.cursor()

    images = conn.cursor()

    images.execute("SELECT Picture FROM Pictures")
    record = images.fetchall()
    for row in record:
        photo = row[0]

        if os.path.exists("myfile.jpg"):
            print("The file exists")
            os.remove("myfile.jpg")
            with open("myfile.jpg", "wb") as fh:
                fh.write(photo)
                fh.close()
            image = face_recognition.load_image_file("myfile.jpg")
            known_face_encodings.append(face_recognition.face_encodings(image)[0])
        else:
            print("The file does not exist")
            with open("/Users/bethnoake/myfile.jpg", "wb") as fh:
                fh.write(photo)
                fh.close()
            image = face_recognition.load_image_file("myfile.jpg")
            known_face_encodings.append(face_recognition.face_encodings(image)[0])





    images.close()



    print("2")


    #userImage = open('userImage.jpg','wb')
    #userImage.write(x[0])

    #unknown_image = face_recognition.load_image_file("lewis.jpg")
    print("1")
    #unknown_face_encoding = face_recognition.face_encodings(unknown_image)[0]

    print("done")



    results = face_recognition.compare_faces(known_face_encodings, unknown_face_encoding)

    print(results)

    count = len(known_face_encodings)


    i = 0
    while i <= count:
        if results[i] == True:
            print((known_face_names[i]))
            break;
        else:
            i = i+1


    conn.close()