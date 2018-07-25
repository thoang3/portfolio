import os


def evaluate_result(filename):

    frame_dir = filename + '_mp4'
    result_file = os.path.join(frame_dir, frame_dir+"_result.txt")
    tp = 0
    fp = 0
    tn = 0
    fn = 0

    with open(result_file, "r") as result_infile:
        for _ in range(3):
            next(result_infile)
        line1 = result_infile.readline().strip().split()

        while True:
            line2 = result_infile.readline()
            if not line2: break  # EOF
            line2 = line2.strip().split()

            file_name = os.path.split(line1[3])[-1].split('.')[0]

            truth_path = os.path.join(frame_dir, "Ground-truth", file_name+'.txt')
            
            if os.path.exists(truth_path):
                with open(truth_path, "r") as truth_infile:
                    truth = truth_infile.readline().strip()
                    
                if line2[0]=='gun:': # gun detected
                    if truth == '1': # true positive
                        tp += 1
                    elif truth == '0': # false positive
                        fp += 1
                    line1 = result_infile.readline().strip().split()
                elif line2[0]=='Enter': # no gun detected
                    if truth == '1': # false negative
                        fn += 1
                    elif truth == '0': # true negative
                        tn += 1
                    line1 = line2
                    
            else:
                if line2[0]=='gun:': # gun detected
                    line1 = result_infile.readline().strip().split()
                elif line2[0]=='Enter': # no gun detected
                    line1 = line2


    precision = tp/(tp+fp)
    recall = tp/(tp+fn)
    return (tp, fp, tn, fn, precision, recall)



if __name__ == "__main__":
    #(TP, FP, TN, FN, Precision, Recall) = evaluate_result("1_mp4")
    print("How many clips folders are there? Make sure your folders are in the format of '1_mp4, 2_mp4, ...', starting with 1.")
    n = int(input("Enter: "))
    totalPrecision = 0
    totalRecall = 0
    print("\n")

    for i in range(1, n+1):
        #print("Result for clip " + str(i) + " " + str(evaluate_result(str(i))))
        (TP, FP, TN, FN, Precision, Recall) = evaluate_result(str(i))
        totalPrecision += Precision
        totalRecall += Recall
        print("Results for clip {}: TP = {}, FP = {}, TN = {}, FN = {}, Precision = {}%, Recall = {}%".format(i, TP, FP, TN, FN, round(Precision * 100, 2), round(Recall * 100, 2)))


    print("\nAverage precision: {}%".format(round(totalPrecision/n*100, 2)))
    print("Average recall: {}%".format(round(totalRecall/n*100, 2)))