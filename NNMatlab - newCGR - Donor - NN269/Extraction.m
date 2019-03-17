clc;
close all;
clear all;

% open cds file
file = 'hmrcds.txt';
fid = fopen(file, 'r');
if fid < 0
    error('Cannot open file: %s', file)
end

% get array of CDS pairs for all sequences from the above CDS file
% and save to ARRAY.mat
i = 1;
while feof(fid) == 0  
    line = fgetl(fid);
    c = textscan(line, '%s');
    array(i).Header = c{1}{1}; % get and store Header for one sequence i
    len = length(c{1});
    c = c{1}(2:len, 1); % get rid of the Header, the rest are CDS pairs
    array(i).CDS_Pairs = c; % store CDS pairs for that same sequence to array
    i = i+1;
end
fclose(fid);
i
% open fasta file
filename = 'hmrfasta.txt';
seqs = fastaread(filename);

SUM = 0; % if SUM = 195 then there's no irregularity in the dataset, for checking purpose, can ignore

% get splice sites' indexes for all sequences
for i = 1:195 % for each sequence i
    
    len = length(array(i).CDS_Pairs); % len/2 is number of pairs of splice sites, i.e. number of introns
    array(i).spliceSiteIndexes = zeros(len-2, 1);  % index of splice sites
    array(i).Status = 1; %  1 means regular else 0 means irregular, for data checking purpose, can ignore
    
    for j = 1:(len/2-1) % for each pair j of donor and acceptor
        first = str2num(array(i).CDS_Pairs{2*j});
        second = str2num(array(i).CDS_Pairs{2*j+1});
        array(i).spliceSiteIndexes(2*j-1) = first + 1;
        array(i).spliceSiteIndexes(2*j) = second -2;
    end
    
    %%%%%%%%%%%%%%%%%%%%%%%%
    % for status checking purpose
    a = array(i).spliceSiteIndexes;
    
    for j = 1:(len-2)
        seqs(i).Sequence(a(j):a(j)+1);
    end
    % Check if all intron starts with donor 'GT' or not
    sum1 = 0;
    for j = 1:(len/2 -1)
        sum1 = sum1 + strcmp(seqs(i).Sequence(a(2*j-1):a(2*j-1)+1),'GT');
    end
    if (sum1 ~= len/2-1)
        array(i).Status = 0; % irregular
    end
    
    % Check if all intron end with acceptor 'AG' or not
    sum2 = 0;
    for j = 1:(len/2 -1)
        sum2 = sum2 + strcmp(seqs(i).Sequence(a(2*j):a(2*j)+1), 'AG');
    end
    if (sum2 ~= len/2-1)
        array(i).Status = 0; % irregular
    end
 
    if (array(i).Status == 1) % if status = 1 then the there's no irregularity
        SUM = SUM + 1;
    end
    
    %%%%%%%%%%%%%%%%%%%%%%%
    %%% sliding windows segmentation,
    windowSize = 40; % for sliding window segmentation

    seq = seqs(i).Sequence;
    %seq = 'ABCDEFGHIJKLMNOPQSTUVYZ'
    %seq = 'ACGTACAGCGTAGAAA';
    l = length(seq);
    %i =1
    s(i) = 0;
    
    [Dimers, Percent] = dimercount(seq);
    total = Dimers.GT; % + Dimers.AG;

    vec = zeros(total, 1);
    j = 1;
    for t = windowSize/2+1:(l - windowSize/2)
        % assign label 0 for every subsequence of length windowSize
        if (strcmp(seq(t:t+1), 'GT'))
            lower = t - windowSize/2 + 1;
            upper = t + windowSize/2;
            inputs(i).subSeq(j).Data = seq(lower:upper);
            inputs(i).subSeq(j).Label = 0;
            
        % now look at the splice site indexes' positions, and assign label 1 for
        % a subsequence that contains a splice site => naive method, need
        % to adapt later to a linear assignment. 
            for x = 1:(len/2-1)
               if (t == a(2*x-1)) 
                   inputs(i).subSeq(j).Label = 1;   
                   vec(j) = 1;
                   s(i)=s(i)+1;
               end  
            end          
            j = j+1;
        end                     
    end
    inputs(i).vectorLabel = vec(1:j-1);
end


save('ARRAY', 'array')
file = strcat('inputs_windowSize_', num2str(windowSize));
save(file, 'inputs')

i
s
%%% Plot inputs(1).vectorLabel
i = 1
%s(i)
%scatter(1:len, vector)
%size(inputs(i).vector)
scatter(1:length(inputs(i).vectorLabel), inputs(i).vectorLabel(1:length(inputs(i).vectorLabel)))

%%
clc
close all;
clear all;

windowSize = 40;

fileName = strcat('inputs_windowSize_', num2str(windowSize));
%fileName = strcat('inputs', '_', num2str(windowSize), '.mat');
load(fileName)

%i=1
k = 1;
n = 150; % number of sequences for training method
        
%%% train data combined, i.e. combined training sets of n sequences
%%% together (l - (ORF-1) - windowSize + 1) j = 1:stepSize:(l - (ORF-1) - windowSize + 1)
for i =1:n
    for j = 1:length(inputs(i).vectorLabel)
        trainDataCombined{k, 1} = inputs(i).subSeq(j).Data;
        trainDataCombined{k, 2} = inputs(i).vectorLabel(j);
        k = k+1;
    end
end

size(trainDataCombined)
save(strcat('dataCombined_', num2str(n), '_sequences_windowSize_', num2str(windowSize)), 'trainDataCombined')

% extract full positive set and random sample negative set
l = 1;
A = [];

for j = 1:length(trainDataCombined)
   if (trainDataCombined{j,2} == 1)
       newTrainDataCombined{l, 1} = trainDataCombined{j, 1};
       newTrainDataCombined{l, 2} = trainDataCombined{j, 2};
       A = [A j];
       l = l+1;
   end
end
length(A)
C = setdiff(1:length(trainDataCombined), A);
length(C)
length(trainDataCombined)
10 * length(A)

[y,idx] = datasample(C, 20 * length(A), 'Replace',false);

for m = 1:length(y)
    newTrainDataCombined{l, 1} = trainDataCombined{m, 1};
    newTrainDataCombined{l, 2} = trainDataCombined{m, 2};
    l = l+1;
end

size(newTrainDataCombined)
save(strcat('newTrainDataCombined_', num2str(n), '_sequences_windowSize_', num2str(windowSize)), 'newTrainDataCombined')


shuffled = randperm(length(newTrainDataCombined));
newTrainDataCombinedRandomized(:, 1) = newTrainDataCombined(shuffled,1);
newTrainDataCombinedRandomized(:, 2) = newTrainDataCombined(shuffled, 2);
newTrainDataCombined = newTrainDataCombinedRandomized;
save(strcat('newTrainDataCombined_', num2str(n), '_sequences_windowSize_', num2str(windowSize), '_randomized'), 'newTrainDataCombined')


