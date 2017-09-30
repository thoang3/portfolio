clear all ;
close all ;
set(0,'DefaultFigureWindowStyle','normal');
drawnow;
clc;


condition = true;

while condition
    
    choice = menu('Please choose a data set!',...
        '  1. Mammals', ...
        '  2. Influenza A virus', ...
        '  3. Coronavirus', ...
        '  4. HRV', ...
        '  5. Bacteria', ...
        '  6. Quit');
    switch choice
        
        case 1
            
            TestUPGMA('Mammals');                    
            
        case 2
            
            TestUPGMA('Influenza');
            
        case 3
                        
            TestUPGMA('Corona');
                        
        case 4
                       
            TestUPGMA('HRV');
                       
        case 5            
            
            TestUPGMA('Bacteria');
                        
        case 6
            
            msgbox('You closed the menu!');
            condition = false;
            
    end
    
end


fprintf('\n');
