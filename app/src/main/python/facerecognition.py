

def main(imageFile):


    print("hello")


    # noinspection PyInterpreter
    import face_recognition
    from PIL import Image
    import pymysql
    db = pymysql.connect(host='local',user='root',passwd='')
    print("3")
    cursor = db.cursor()
    query = ("SHOW DATABASES")
    cursor.execute(query)
    for r in cursor:
        print (r)


    #file = ("/Users/bethnoake/AndroidStudioProjects/camera/app/src/main/python/Aniston.jpg" )

    #img = Image.open(f)
    import os.path



    #from os.path import dirname, join
    #f = open(join(dirname(__file__), 'Aniston.jpg'))
    print("2")


    aniston_image = face_recognition.load_image_file(file1)
    aniston_face_encoding = face_recognition.face_encodings(aniston_image)[0]


    unknown_image = face_recognition.load_image_file(imageFile)
    unknown_face_encoding = face_recognition.face_encodings(unknown_image)[0]

    #plt.imshow(imageFile)


    print("1")



    known_face_encodings = [
        aniston_face_encoding

    ]
    known_face_names = [
        "Jennifer Aniston"

    ]

    # Now we can see the two face encodings are of the same person with `compare_faces`!

    results = face_recognition.compare_faces(known_face_encodings, unknown_face_encoding)



    count = len(known_face_encodings)


    i = 0
    while i <= count:
        if results[i] == True:
            print( (known_face_names[i]))
            break;
        else:
            i = i+1

