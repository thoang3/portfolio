%% Draw phylogenetic tree representing evolutionary relations 
%% between DNA sequences.
%
% Input:  Fasta file containing DNA dataset.
% Output: 1. Phylogenetic tree representing evolutionary relations between
%            those DNA sequences
%         2. Image file of the above phylogenetic tree (.eps file)
%         3. Distance matrix file (.meg file) in order to use with MEGA 6
%            for a better visulization of the above phylogenetic tree. 
%         4. Runng time is saved to file RunningTime.txt
%
% Tung Hoang, Ph.D candidate
% Dept. of Mathematics, Statistics and Computer Science
% University of Illinois at Chicago, Chicago IL, USA
% Last update 05/15/2016
%
% Citation:
% Hoang,T., Yin, C., & Yau, S. S. T. (2016). Numerical encoding of DNA sequences by Chaos Game Representation
% with application in similarity comparison. Genomics, Vol 107, 2016, Elsevier Inc.

function TestCgrDft(name)

tStart = tic;

file=strcat(name,'.fasta');
filename=strcat(name, '-DistanceData-Matlab-CgrDft-', date, '.meg');
seqs = fastaread(file);
len = length(seqs);

for i = 1:len
     lenX(i)=length(seqs(i).Sequence);
end
lenX
b=max(lenX);

fprintf('Min: %d \n', min(lenX));
fprintf('Max: %d \n', max(lenX));


%Get moment vectors
for i=1:len
            vec  = cgrDft(seqs(i).Sequence); % compute power spectrum
            v{i} = evenScaleVector(vec, b); % even scaling
           
end

display(v)

%Get (Euclidean) lower triangular distance matrix based on above moment vectors
for j=1:len
    for i=j:len        
               D(i,j)=getEDistance(v{i}, v{j});   
    end 
end 

%Rearrange the above distance matrix into a row vector in order to
%use seqlinkage
d=squareform(D);
% for j=1:(len-1)
%     for i=j+1:len
%         d((j-1)*(2*len-2-j)/2+i-1)=D(i,j); 
%     end
% end

%Phylogenetic tree
tree= seqlinkage(d,'average',seqs);
%tree= seqneighjoin(d,'equivar',seqs)
h = plot(tree, 'orient', 'left');
title('Similarity distance using our new CgrDft method with UPGMA', 'FontName', 'AvantGarde','FontSize', 10,'FontWeight','bold')

%Set PaperPositionMode to auto so that the exported figure looks like it does on the screen.
set(gcf, 'PaperPositionMode', 'auto');
print('-depsc2', strcat(name, '-CgrDft-', date, '.eps'));

%print('-dpng', strcat(name, '.png'));
%print('-dpdf', strcat(name, '.pdf'));

tEnd = toc(tStart);

fprintf('%d minutes and %f seconds\n',floor(tEnd/60),rem(tEnd,60));
fid=fopen('Running Time.txt', 'a');
fprintf(fid, '%s, CgrDft, %s: %d minutes and %f seconds\r\n', name, date, floor(tEnd/60),rem(tEnd,60));
fclose(fid);


%% code to export distance matrix to standard form (.meg file) in order to use with Mega 6. 
fid=fopen(filename, 'w');
fprintf(fid, '%s\r\n', '#mega');
fprintf(fid, '%s\r\n', '!Title: Phylogenetic Analysis;');
fprintf(fid, '%s%d%s\r\n\', '!Format DataType=Distance DataFormat=LowerLeft NTaxa=', len, ';');
fprintf(fid, '\r\n');

for i=1:len

    fprintf(fid, '%c%d%c %c%s\r\n', '[', i, ']', '#', seqs(i).Header);
    
end

fprintf(fid, '%c ', '[');
for i=1:len
    
fprintf(fid, '%d ', i);

end
fprintf(fid, '%c\r\n', ']');

for i=1:len

    fprintf(fid, '%c%d%c', '[', i, ']');
   for j=1:i-1
       
    fprintf(fid, ' %f', D(i, j));
   
   end 
    fprintf(fid, '\r\n');
end

fclose(fid);

end

