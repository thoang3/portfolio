from darkflow.net.build import TFNet
import cv2

from io import BytesIO
import time
import requests
from PIL import Image
import numpy as np
import smtplib
from email.mime.text import MIMEText
from email.mime.image import MIMEImage
from email.mime.multipart import MIMEMultipart
#from io import StringIO


options = {"model": "cfg/yolov2-fedtest.cfg", "load": "bin/yolov2-fed_33000.weights", "gpu": 1.0, "threshold": 0.4}
#options = {"model": "cfg/yolov2.cfg", "load": "bin/yolov2.weights", "threshold": 0.1}

tfnet = TFNet(options)

DeliverySeen = 0


while True:
    r = requests.get('http://192.168.1.84:5000/image.jpg') # replace with your ip address
    curr_img = Image.open(BytesIO(r.content))
    curr_img_cv2 = cv2.cvtColor(np.array(curr_img), cv2.COLOR_RGB2BGR)

    # uncomment below to try your own image
    #imgcv = cv2.imread('./sample/1.png')
    result = tfnet.return_predict(curr_img_cv2)
    #print(result)
    for detection in result:
        if detection['label'] == 'FedEx':
            print("FedEx detected")

            cv2.rectangle(curr_img_cv2, (detection["topleft"]["x"], detection["topleft"]["y"]),
              (detection["bottomright"]["x"], detection["bottomright"]["y"]), (0, 255, 0), 4)
            text_x, text_y = detection["topleft"]["x"] - 10, detection["topleft"]["y"] - 10

            cv2.putText(curr_img_cv2, detection['label'], (text_x, text_y),
                    cv2.FONT_HERSHEY_SIMPLEX, 0.8, (0, 255, 0), 2, cv2.LINE_AA)

            DeliverySeen += 1
            cv2.imwrite('Delivery/%i.jpg' % DeliverySeen, curr_img_cv2)

            memf = BytesIO()
            curr_img.save(memf, "JPEG")
            img = MIMEImage(memf.getvalue())

            msg = MIMEMultipart()
            msg['Subject'] = 'Detection'
            msg['From'] = 'thanhtung1984@gmail.com'
            msg['To'] = 'hoangthanhtungvn@gmail.com'
            text = MIMEText("FedEx detected!!!")
            msg.attach(text)
            msg.attach(img)

            server = smtplib.SMTP('smtp.gmail.com', 587)
            server.starttls()
            server.login("thanhtung1984@gmail.com", "RaspberryPi2817")

            server.sendmail("thanhtung1984@gmail.com", "hoangthanhtungvn@gmail.com", msg.as_string())
            server.sendmail("thanhtung1984@gmail.com", "lukicm76@gmail.com", msg.as_string())           
            server.quit()

        elif detection['label'] == 'UPS':
            print("UPS detected")

            cv2.rectangle(curr_img_cv2, (detection["topleft"]["x"], detection["topleft"]["y"]),
              (detection["bottomright"]["x"], detection["bottomright"]["y"]), (0, 255, 0), 4)
            text_x, text_y = detection["topleft"]["x"] - 10, detection["topleft"]["y"] - 10

            cv2.putText(curr_img_cv2, detection['label'], (text_x, text_y),
                    cv2.FONT_HERSHEY_SIMPLEX, 0.8, (0, 255, 0), 2, cv2.LINE_AA)

            DeliverySeen += 1
            cv2.imwrite('Delivery/%i.jpg' % DeliverySeen, curr_img_cv2)

            memf = BytesIO()
            curr_img.save(memf, "JPEG")
            img = MIMEImage(memf.getvalue())

            msg = MIMEMultipart()
            msg['Subject'] = 'Detection'
            msg['From'] = 'thanhtung1984@gmail.com'
            msg['To'] = 'hoangthanhtungvn@gmail.com'
            text = MIMEText("UPS detected!!!")
            msg.attach(text)
            msg.attach(img)

            server = smtplib.SMTP('smtp.gmail.com', 587)
            server.starttls()
            server.login("thanhtung1984@gmail.com", "RaspberryPi2817")

            server.sendmail("thanhtung1984@gmail.com", "hoangthanhtungvn@gmail.com", msg.as_string())
            server.sendmail("thanhtung1984@gmail.com", "lukicm76@gmail.com", msg.as_string())           
            server.quit()

        elif detection['label'] == 'USPS':
            print("USPS detected")

            cv2.rectangle(curr_img_cv2, (detection["topleft"]["x"], detection["topleft"]["y"]),
              (detection["bottomright"]["x"], detection["bottomright"]["y"]), (0, 255, 0), 4)
            text_x, text_y = detection["topleft"]["x"] - 10, detection["topleft"]["y"] - 10

            cv2.putText(curr_img_cv2, detection['label'], (text_x, text_y),
                    cv2.FONT_HERSHEY_SIMPLEX, 0.8, (0, 255, 0), 2, cv2.LINE_AA)

            DeliverySeen += 1
            cv2.imwrite('Delivery/%i.jpg' % DeliverySeen, curr_img_cv2)

            memf = BytesIO()
            curr_img.save(memf, "JPEG")
            img = MIMEImage(memf.getvalue())

            msg = MIMEMultipart()
            msg['Subject'] = 'Detection'
            msg['From'] = 'thanhtung1984@gmail.com'
            msg['To'] = 'hoangthanhtungvn@gmail.com'
            text = MIMEText("USPS detected!!!")
            msg.attach(text)
            msg.attach(img)

            server = smtplib.SMTP('smtp.gmail.com', 587)
            server.starttls()
            server.login("thanhtung1984@gmail.com", "RaspberryPi2817")

            server.sendmail("thanhtung1984@gmail.com", "hoangthanhtungvn@gmail.com", msg.as_string())
            server.sendmail("thanhtung1984@gmail.com", "lukicm76@gmail.com", msg.as_string())           
            server.quit()

    print('running again')
    time.sleep(4)



 
