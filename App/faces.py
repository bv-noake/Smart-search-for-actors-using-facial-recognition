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

known_face_encodings = []


conn = mysql.connector.connect(
        host="192.168.0.40",
        port = "3308",
        user="username",
        passwd="password",
        db = "Project"
)


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

conn.close()
