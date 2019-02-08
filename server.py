# -*- coding: utf-8 -*-
"""
Created on Thu Feb  7 12:38:09 2019

@author: User
"""

import socket
import sys, win32com.client
import wmi
import time
HOST = ''  # IP Address
PORT = 65432        # Port to listen on (non-privileged ports are > 1023)
shell = win32com.client.Dispatch("WScript.Shell")
brightness = 0
while True:
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.bind((HOST, PORT))
        s.listen()
        conn, addr = s.accept()
        with conn:
            print('Connected by', addr)
            while True:
                data = conn.recv(1024)
                if not data:
                    break
                print(data)
                if data == b'0':
                    time.sleep(0.02)
                    conn.send('0\n')
                elif data == b'A':
                    shell.SendKeys(chr(175),0) #vol up
                elif data== b'B':
                    shell.SendKeys(chr(174),0) #vol down
                elif data == b'C':
                    if brightness < 100:
                        brightness += 10 
                    wmi.WMI(namespace='wmi').WmiMonitorBrightnessMethods()[0].WmiSetBrightness(brightness, 0) #bright up
                elif data == b'D':
                    if brightness > 0:
                        brightness -= 10 
                    wmi.WMI(namespace='wmi').WmiMonitorBrightnessMethods()[0].WmiSetBrightness(brightness, 0) #bright down
                elif data == b'E':
                    shell.SendKeys(chr(32),0) #spacebar
                elif data == b'F':
                    shell.SendKeys("{LEFT}",0) #left arrow
                elif data == b'G':
                    shell.SendKeys("{RIGHT}",0) #right arrow
                elif data == b'H':
                    shell.SendKeys(chr(70),0) #F11
                