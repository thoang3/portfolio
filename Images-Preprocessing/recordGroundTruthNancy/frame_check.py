import tkinter
from PIL import Image, ImageTk
import os
import glob


class FrameCheck:
    def __init__(self, master, frame_dir_idx=1):
        self.master = master
        self.master.title("Frame Check")

        self.frame_dir_idx = frame_dir_idx
        self.frame_dir = str(frame_dir_idx)+"_mp4"
        self.frame_files = sorted(glob.glob(os.path.join(self.frame_dir, "Frames", '*.jpg')))
        self.idx = 0
        self.num_files = len(self.frame_files)
        
        self.canvas = tkinter.Canvas(master, width=1200, height=800)
        self.canvas.pack()

        self.label = tkinter.Label(master, text="Does this contain a gun?")
        self.label.pack()

        self.btn_yes=tkinter.Button(master, text="YES", width=50, command=self.yes)
        self.btn_yes.pack(anchor=tkinter.CENTER, expand=True)
        
        self.btn_no=tkinter.Button(master, text="NO", width=50, command=self.no)
        self.btn_no.pack(anchor=tkinter.CENTER, expand=True)
        
        self.load_image()
        
    def load_image(self):
        image_path = self.frame_files[self.idx]
        self.img = Image.open(image_path)
        w, h = self.img.size
        scale_ratio = min(1200/w,800/h)
        self.img = self.img.resize((int(scale_ratio*float(w)), int(scale_ratio*float(h))))
        self.tkimg = ImageTk.PhotoImage(self.img)
        self.canvas.create_image(0, 0, image = self.tkimg, anchor=tkinter.NW)
    
    def write(self, yn):
        frame = self.frame_files[self.idx]
        frame_name = os.path.split(frame)[-1].split('.')[0]
        with open(os.path.join(self.frame_dir, "Ground-truth", frame_name+".txt"), "w+") as outfile:
            if yn == True:
                outfile.write("1")
            elif yn == False:
                outfile.write("0")
        self.idx += 1
        if self.idx < self.num_files:
            self.load_image()
        else:
            self.label.config(text="Finished. You may close this window.")
            self.btn_yes.config(state=tkinter.DISABLED)
            self.btn_no.config(state=tkinter.DISABLED)

    def yes(self):
        self.write(True)
    
    def no(self):
        self.write(False)



if __name__ == '__main__':
    root = tkinter.Tk()
    tool = FrameCheck(root)
    root.mainloop()
