function [EDist] = getEDistance(A,B)
% Get euclidean distance
% Number of column of A and B are dimensional, must be same
% Rows is the data points
%8/2/2013 C. Yin.

%A=[1 2 3 -1;4 5 6 -1] % The 4-D space, two points in A
%B=[-1 3 2 4;-2 5 4 -1] % The 4-D space, two points in B

%A=[1 2 3;4 5 3] % The 3-D space, two points in A
%B=[-1 3 2;3 4 -1] % The 3-D space, two points in B
%
% Citation:
% Hoang,T., Yin, C., & Yau, S. S. T. (2016). Numerical encoding of DNA sequences by Chaos Game Representation
% with application in similarity comparison. Genomics, Vol 107, 2016, Elsevier Inc.

diffSquare=(A-B).^2;
EDist=sqrt(sum(diffSquare,2));

end

