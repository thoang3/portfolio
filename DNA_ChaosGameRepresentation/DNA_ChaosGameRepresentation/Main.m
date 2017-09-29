%% Main program to run the algorithm
% 
% 
% Tung Hoang, Ph.D candidate
% Dept. of Mathematics, Statistics and Computer Science
% University of Illinois at Chicago, Chicago IL, USA
% Last update 05/15/2016
%
% Citation:
% Hoang,T., Yin, C., & Yau, S. S. T. (2016). Numerical encoding of DNA sequences by Chaos Game Representation
% with application in similarity comparison. Genomics, Vol 107, 2016, Elsevier Inc.

clear all ;
close all ;
set(0,'DefaultFigureWindowStyle','normal');
drawnow;
clc;


condition = true;

while condition
    
    choice = menu('Please choose a data set!',...
        '  1. Human Rhinovirus', ...
        '  2. Mammals', ...
        '  3. Influenza', ...
        '  4. HPV', ...
        '  5. Quit');
    switch choice
        
        case 1
            
            TestCgrDft('HRV');
            
        case 2
            
            TestCgrDft('Mammals');
            
        case 3
            
            TestCgrDft('Influenza');
            
        case 4
            
            TestCgrDft('HPV');
            
        case 5
            
            msgbox('You closed the menu!');
            condition = false;
            
    end
    
end


fprintf('\n');
