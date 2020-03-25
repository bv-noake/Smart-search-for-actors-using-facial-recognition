#!/Library/Frameworks/Python.framework/Versions/3.8/bin/python3.8

import os
from stat import *
import sys

import face_recognition

import mysql.connector
import cv2
import pickle
import pathlib
import os
import base64
import PIL.Image

from PIL import Image


known_face_names = []
known_face_encodings = []

def rotate_img(img_path, rt_degr):
    img = Image.open(img_path)
    return img.rotate(rt_degr, expand=1)




conn = mysql.connector.connect(
        host="192.168.43.35",
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


    with open("myfile.jpg", "wb") as fh:
        fh.write(photo)
        fh.truncate()
        fh.close()
    imageknown = face_recognition.load_image_file("myfile.jpg")
    known_face_encodings.append(face_recognition.face_encodings(imageknown)[0])




images.close()


img_rt_360 = rotate_img('unknown.jpg', 270)
img_rt_360.save('unknown.jpg')

try:
    unknown_image = face_recognition.load_image_file("unknown.jpg")
    print("1")
    unknown_face_encoding = face_recognition.face_encodings(unknown_image)[0]

except IndexError:
    print("I wasn't able to locate any faces in at least one of the images. Check the image files. Aborting...")
    quit()


print("2")



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
