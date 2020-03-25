#!/Library/Frameworks/Python.framework/Versions/3.8/bin/python3

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

conn = mysql.connector.connect(
        host="192.168.0.40",
        port = "3308",
        user="username",
        passwd="password",
        db = "Project"
)

known_face_names = []


names = conn.cursor()

names.execute("SELECT FName, LName FROM Celebrities")
for x in names:
        known_face_names.append(x)

print(known_face_names)

names.close()

conn.close()
