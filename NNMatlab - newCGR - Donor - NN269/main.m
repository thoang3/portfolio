% Solve a Pattern Recognition Problem with a Neural Network
% Created Sat Nov 19 13:39:27 CST 2016
%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% INITIAL SETUP
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
clc
close all
clear all

rep = 10;

hiddenLayerSize = [];

for i = 5:5:60
    hiddenLayerSize = [hiddenLayerSize, ones(1, rep)*i]
    
end

arr1 = zeros(1, rep);
arr2 = zeros(1, rep);

for kk = 1:length(hiddenLayerSize)
    
    clc
    close all
    clear net
    
    windowSize = 15
    
    M = 2 * windowSize;  % input (vector) size
    
    
    % name of data file (donor and acceptor sequences)
    nameP = 'Donor_Train_Positive';
    fileP=strcat(nameP,'.fasta');
    
    seqsP = fastaread(fileP);
    lenP = length(seqsP);
    
    for i = 1:lenP
        seqsP(i).Class = 1;
    end
    
    nameN = 'Donor_Train_Negative';
    fileN=strcat(nameN,'.fasta');
    
    seqsN = fastaread(fileN);
    lenN = length(seqsN);
    
    for i = 1:lenN
        seqsN(i).Class = 0;
    end
    
    combinedTrain = [seqsP; seqsN];
    
    shuffled = randperm(length(combinedTrain));
    combinedTrainRandomized = combinedTrain(shuffled,1);
    
    newCombined = struct2cell(combinedTrainRandomized);
    
    inputsTrainData = newCombined(2, :);
    targetsTrain = cell2mat(newCombined(3,:));
    
    n = length(inputsTrainData) % number of input data
    
    inputsTrain = zeros(M, n);
    %  1:stepSize:(l - (ORF-1) - windowSize + 1)
    %j = 1;
    for i = 1:n
        %inputs(:,i) = GetMomentVectorPS(inputsData{i}); % cgrDft(inputsData{i})';
        inputsTrain(:,i) = cgr(inputsTrainData{i})';
        %j = j+1;
        %vec = cgrDft(inputsData{i}{1});
        %inputs(:, i) = evenScaleVector(vec, M)';
    end
    %j
    
    % Create a Pattern Recognition Network
    
    net = patternnet(hiddenLayerSize(kk));
    net.inputs{1}.size = M;
    
    % Choose Input and Output Pre/Post-Processing Functions
    % For a list of all processing functions type: help nnprocess
    net.inputs{1}.processFcns = {'removeconstantrows','mapminmax'};
    net.outputs{2}.processFcns = {'removeconstantrows','mapminmax'};
    
    net.layers{1}.transferFcn = 'tansig';
    net.layers{2}.transferFcn = 'tansig';
    % tansig + logsig DON'T work!
    
    % Setup Division of Data for Training, Validation, Testing
    % For a list of all data division functions type: help nndivide
    net.divideFcn = 'dividerand';  % Divide data randomly
    net.divideMode = 'sample';  % Divide up every sample
    net.divideParam.trainRatio = 70/100;
    net.divideParam.valRatio = 15/100;
    net.divideParam.testRatio = 15/100;
    
    % For help on training function 'trainlm' type: help trainlm
    % For a list of all training functions type: help nntrain
    net.trainFcn = 'trainlm';  % Levenberg-Marquardt
    %net.trainFcn = 'trainrp';
    %net.trainFcn = 'trainscg';
    
    net.efficiency.memoryReduction = 30; % for large input data
    
    % Choose a Performance Function
    % For a list of all performance functions type: help nnperformance
    net.performFcn = 'mse';  % Mean squared error
    
    % Choose Plot Functions
    % For a list of all plot functions type: help nnplot
    net.plotFcns = {'plotperform','plottrainstate','ploterrhist', ...
        'plotregression', 'plotfit'};
    
    net.trainParam.epochs = 100;
    
    for i = 1:3
        
        tStart = tic;
        
        % Train the Network
        [net,tr] = train(net,inputsTrain,targetsTrain);
        
        tEnd = toc(tStart);
        
        % Test the Network
        outputsTrain = net(inputsTrain);
        
        %figure, plotconfusion(targets,outputs)
        %figure, plotroc(targets, outputs)
        
        %[X,Y,T,AUC,OPTROCPT] = perfcurve(targets,outputs,1);
        %cutoff = OPTROCPT(2)
        %outputs = double(outputs >= cutoff); % Assigns at optimal ROC value
        %cutoff = 0.9
        
        errors = gsubtract(targetsTrain,outputsTrain);
        performance = perform(net,targetsTrain,outputsTrain);
        
        % Recalculate Training, Validation and Test Performance
        trainTargets = targetsTrain .* tr.trainMask{1};
        valTargets = targetsTrain  .* tr.valMask{1};
        testTargets = targetsTrain  .* tr.testMask{1};
        trainPerformance = perform(net,trainTargets,outputsTrain);
        valPerformance = perform(net,valTargets,outputsTrain);
        testPerformance = perform(net,testTargets,outputsTrain);
        
        epochs = net.trainParam.epochs;
        time = net.trainParam.time;
        goal = net.trainParam.goal;
        min_grad = net.trainParam.min_grad;
        mu_max = net.trainParam.mu_max;
        max_fail = net.trainParam.max_fail;
        show = net.trainParam.show;
        
        % View the Network
        %view(net)
        
        
        
        
        %figure, plotroc(targetsTrain, outputsTrain)
        %title('train ROC');
        % Plots
        % Uncomment these lines to enable various plots.
        %figure, plotperform(tr)
        %figure, plottrainstate(tr)
        %name = strcat('Confusion_', fileName, '_', num2str(M), '_', num2str(hiddenLayerSize), '_', net.layers{1}.transferFcn, '_', net.layers{2}.transferFcn, '_', date, '_', num2str(i));
        %title(name, 'FontName', 'AvantGarde','FontSize', 10,'FontWeight','bold')
        %figure, plotroc(targets, outputs)
        %figure, ploterrhist(errors)
        %Set PaperPositionMode to auto so that the exported figure looks like it does on the screen.
        %print('-depsc2', strcat(name,'.eps'));
        
        
        if (i==3)
            [c,cm,ind,per] = confusion(targetsTrain, outputsTrain)
            %[C,order] = confusionmat(targetsTrain, outputsTrain);
            %figure, plotroc(targetsTrain, outputsTrain)
            %title('train ROC');
            
            name = strcat('Confusion_Donor_Training', '_', num2str(M), '_', num2str(hiddenLayerSize(kk)), '_', net.layers{1}.transferFcn, '_', net.layers{2}.transferFcn, '_', date);
            %figure, plotconfusion(targetsTrain,outputsTrain)
            %title('Training');
            %set(gcf, 'PaperPositionMode', 'auto');
            %print('-depsc2', strcat(name,'.eps'));
            
            fid=fopen('Running Time Donor.txt', 'a');
            fprintf(fid, '\r\n');
            fprintf(fid, '%s\r\n', datetime);
            fprintf(fid, 'window size = %d, hidden layer size = %d\r\n', windowSize, hiddenLayerSize(kk));
            fprintf(fid, 'Activation Functions: %s and %s\r\n', net.layers{1}.transferFcn, net.layers{2}.transferFcn);
            %fprintf(fid, 'Training Sensitivity and Precision: %f and %f\r\n', cm(2,2)/(cm(2,2) + cm(2,1)), cm(2,2)/(cm(2,2) + cm(1,2)));
            
            [X_train,Y_train,T_train,auROC_train, OPTROCPT_train] = perfcurve(targetsTrain,outputsTrain,1); %
            %fprintf(fid, 'Train auROC: %f \r\n', auROC_train);
            
            % Draw Precision-Recall (auPRC)
            [Xpr_train,Ypr_train,Tpr_train,auPRC_train] = perfcurve(targetsTrain,outputsTrain,1, 'xCrit', 'reca', 'yCrit', 'prec'); %
            figure
            plot(Xpr_train,Ypr_train)
            xlabel('Recall'); ylabel('Precision')
            title(['Precision-recall curve (Train auPRC: ' num2str(auPRC_train) ')'])
            
            fprintf(fid, 'Train auROC: %f   ,Train auPRC: %f\r\n', auROC_train, auPRC_train);
            
            % Draw both AUC (auROC) and Precision-Recall (auPRC)
            prec_rec(outputsTrain,targetsTrain);
            
            
            fclose(fid);
        end
        %fid=fopen('Running Time.txt', 'a');
        %fprintf(fid, 'i = %d\r\n', i);
        %fprintf(fid, '%s\r\n', fileName);
        %fprintf(fid, '%s\r\n', name);
        %fprintf('%d minutes and %f seconds\n',floor(tEnd/60),rem(tEnd,60));
        %fprintf(fid, '%s, %s: %d minutes and %f seconds\r\n', name, date, floor(tEnd/60),rem(tEnd,60));
        %fprintf(fid, 'cutoff: %f\r\n', cutoff);
        %fclose(fid);
        
        %netFileName = strcat('newTrainNet_', fileName, '_', num2str(M), '_', num2str(hiddenLayerSize), '_', net.layers{1}.transferFcn, '_', net.layers{2}.transferFcn, '_', date, '_', num2str(i));
        % save(netFileName, 'net')
        
        % Testing
        % name of data file (donor and acceptor sequences)
        nameTestP = 'Donor_Test_Positive';
        fileTestP=strcat(nameTestP,'.fasta');
        
        seqsTestP = fastaread(fileTestP);
        lenTestP = length(seqsTestP);
        
        for ii = 1:lenTestP
            seqsTestP(ii).Class = 1;
        end
        
        nameTestN = 'Donor_Test_Negative';
        fileTestN=strcat(nameTestN,'.fasta');
        
        seqsTestN = fastaread(fileTestN);
        lenTestN = length(seqsTestN);
        
        for ii = 1:lenTestN
            seqsTestN(ii).Class = 0;
        end
        %fileName = 'dataCombined_50_sequences_windowSize_351_stepSize_3_ORF_1';
        
        combinedTest = [seqsTestP; seqsTestN];
        
        shuffledTest = randperm(length(combinedTest));
        combinedTestRandomized = combinedTest(shuffledTest,1);
        
        newCombinedTest = struct2cell(combinedTestRandomized);
        
        inputsTestData = newCombinedTest(2, :);
        targetsTest = cell2mat(newCombinedTest(3,:));
        
        
        %n = length(ExonIntronData)
        nn = length(inputsTestData)
        %even scale to M
        inputsTest = zeros(M, nn);
        j = 1;
        for ii = 1:nn
            %inputs(:,i) = GetMomentVectorPS(inputsData{i}); % cgrDft(inputsData{i})';
            inputsTest(:,ii) = cgr(inputsTestData{ii})';
            j = j+1;
            %vec = cgrDft(inputsData{i}{1});
            %inputs(:, i) = evenScaleVector(vec, M)';
        end
        
        for ii = 1:nn
            %vec = cgrDft(seq(i:i+299));
            outputsTest(ii) = sim(net, inputsTest(:,ii));
        end
        
        %outputs = double(outputs >= 0.7); % Assigns at optimal ROC value
        
        
        %figure, plotroc(targetsTest, outputsTest)
        %title('test');
        
        if (i==3)
            %[c,cm,ind,per] = confusion(targetsTest, outputsTest)
            [cTest,cmTest,indTest,perTest] = confusion(targetsTest, outputsTest)
            
            name = strcat('Confusion_Donor_Testing', '_', num2str(M), '_', num2str(hiddenLayerSize(kk)), '_', net.layers{1}.transferFcn, '_', net.layers{2}.transferFcn, '_', date);
            figure, plotconfusion(targetsTest,outputsTest)
            title('Testing');
            set(gcf, 'PaperPositionMode', 'auto');
            print('-depsc2', strcat(name,'.eps'));
            
            fid1=fopen('Running Time Donor.txt', 'a');
            fprintf(fid1, 'Testing Sensitivity and Precision: %f and %f\r\n', cmTest(2,2)/(cmTest(2,2) + cmTest(2,1)), cmTest(2,2)/(cmTest(2,2) + cmTest(1,2)));
            [X_test,Y_test,T_test,auROC_test,OPTROCPT_test] = perfcurve(targetsTest,outputsTest,1);
            %fprintf(fid1, 'Test auROC: %f\r\n', auROC_test);
            
            % Draw Precision-Recall (auPRC)
            [Xpr_test,Ypr_test,Tpr_test,auPRC_test] = perfcurve(targetsTest,outputsTest,1, 'xCrit', 'reca', 'yCrit', 'prec'); %
            figure
            plot(Xpr_test,Ypr_test)
            xlabel('Recall'); ylabel('Precision')
            title(['Precision-recall curve (Test auPRC: ' num2str(auPRC_test) ')'])
            
            fprintf(fid1, 'Test auROC: %f   ,Test auPRC: %f\r\n', auROC_test, auPRC_test);
            
            b = mod(kk, rep);
            if (b==0) 
                b = rep;
            end;                
            
            arr1(b) = auROC_test;
            arr2(b) = auPRC_test;
            
            if (b==rep)
               fprintf(fid1, '\r\n'); 
               fprintf(fid1, '****************** Mean Test auROC: %f, Mean Test auPRC: %f\r\n', mean(arr1), mean(arr2));
               arr1 = zeros(1, rep);
               arr2 = zeros(1, rep);
            end
          
            % Draw both AUC and Precision-Recall
            prec_rec(outputsTest,targetsTest);
            
            fclose(fid1);
        end
        %figure
        %scatter(1:nn, targetsTest)
        %figure
        %scatter(1:nn, outputsTest)
        
    end
    
end