function TestUPGMA(name)
%UNTITLED Summary of this function goes here
%   Detailed explanation goes here
tStart = tic;

file=strcat(name,'.fasta');
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
    
           v{i}=1/b*GetMomentVectorPS(seqs(i).Sequence);
           
end

%Get (Euclidean) lower triangular distance matrix based on above moment vectors
for j=1:len
    for i=j:len        
               D(i,j)=getEDistance(v{i}, v{j});   
    end 
end 

%Rearrange the above distance matrix into a row vector in order to
%use seqlinkage
d=squareform(D);

%Phylogenetic tree
tree= seqlinkage(d,'average',seqs);
h = plot(tree, 'orient', 'left');
title('Similarity distance using our new method with UPGMA', 'FontName', 'AvantGarde','FontSize', 10,'FontWeight','bold')

tEnd = toc(tStart);

fprintf('%d minutes and %f seconds\n',floor(tEnd/60),rem(tEnd,60));


end

