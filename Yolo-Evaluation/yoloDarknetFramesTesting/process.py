import glob, os

# Current directory

def process(name):

	os.chdir('C:/Users/thoan/Aegis-AI/Yolo-Evaluation/yoloDarknetFramesTesting/' + name + '_mp4/Frames')
	print(os.getcwd())

	current_dir = os.path.dirname(os.path.abspath(__file__))
	print(current_dir)
	path_data = current_dir.replace('\\', '/')
	print(path_data)

	# Percentage of images to be used for the test set
	percentage_test = 100;

	# Create and/or truncate train.txt and test.txt
	file_train = open('train.txt', 'w')
	file_test = open('test.txt', 'w')

	# Populate train.txt and test.txt
	counter = 1
	index_test = round(100 / percentage_test)
	for pathAndFilename in glob.iglob(os.path.join(current_dir, "*.jpg")):
		title, ext = os.path.splitext(os.path.basename(pathAndFilename))
		if counter == index_test:
			counter = 1
			print(path_data + title + '.jpg' + "\n")
			file_test.write(path_data + '/' + title + '.jpg' + "\n")
		else:
			counter = counter + 1
			file_train.write(path_data + '/' + title + '.jpg' + "\n")


if __name__ == "__main__":

    print("How many clips folders are there? Make sure your folders are in the format of '1_mp4, 2_mp4, ...', starting with 1.")
    n = int(input("Enter: "))

    for i in range(1, n+1):
    	process(str(i))
