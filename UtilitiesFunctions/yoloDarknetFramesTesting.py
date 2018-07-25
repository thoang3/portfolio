import os
import subprocess
import time
import datetime


def testYoloFramesList(name, epoch=16000):

    #print(os.getcwd())
    os.chdir("C:/Users/thoan/Aegis-AI/darknet/build/darknet/x64")
    print(os.getcwd())

    _list = ['darknet.exe', 'detector', 'test', 'data/gun3222.data', 'cfg/yolov3-gun3222test.cfg', 
         '', '-i', '0', '-dont_show', '-ext_output', '<', '', '>', 'result.txt']


    #epoch = 14000
    print("Epoch is: " + str(epoch) + "\n")
    _list[5] = 'backup/gun5479_AWS/yolov3-gun3222_' + str(epoch) + '.weights'
    #print(_list[5])
    _list[11] = 'C:/Users/thoan/Aegis-AI/Yolo-Evaluation/yoloDarknetFramesTesting/' + name + '_mp4/Frames/test.txt'

    _list[13] = 'C:/Users/thoan/Aegis-AI/Yolo-Evaluation/yoloDarknetFramesTesting/' + name + '_mp4/' + name + '_mp4_gun5479_' + str(epoch) + '_result.txt' 

    print("Command and output: " + ' '.join(_list))#str(_list))

    try:
        #print('tung')
        #txt_outpath = 'D:/Yolo-Results/' + str(i) + '.txt'
        #txt_outfile = open(txt_outpath, "w")
        #txt_outfile.close()
        subprocess.call(_list, shell=True)

    except KeyboardInterrupt:
        print('\n\nKeyboard exception received. Exiting.')
        exit()

if __name__ == "__main__":


    print("How many clips folders are there? Make sure your folders are in the format of '1_mp4, 2_mp4, ...', starting with 1.")
    n = int(input("Enter: "))
    
    for i in range(1, n+1):
        testYoloFramesList(str(i))

