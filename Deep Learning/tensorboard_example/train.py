import torch
import torch.nn as nn
import torchvision
from torchvision import transforms
from logger import Logger
from mnist import MNIST_MLP, MNIST_CNN
from tensorboard import TensorBoard

# Device configuration
device = torch.device("cuda" if torch.cuda.is_available() else "cpu")

def main():

    # Hyperparameters
    num_epochs = 10
    batch_size = 100
    learning_rate = 0.001

    # MNIST dataset 
    train_dataset = torchvision.datasets.MNIST(root='../../data', 
                                     train=True, 
                                     transform=transforms.ToTensor(),  
                                     download=True)

    valid_dataset = torchvision.datasets.MNIST(root='../../data',
					  train=False,
					  transform=transforms.ToTensor())
    print(len(train_dataset))
    print(len(valid_dataset))

    # Data loader
    train_loader = torch.utils.data.DataLoader(dataset=train_dataset, 
                                          batch_size=batch_size, 
                                          shuffle=True)
    valid_loader = torch.utils.data.DataLoader(dataset=valid_dataset,
					  batch_size=batch_size,
					  shuffle=False)

    print(len(train_loader))
    print(len(valid_loader))

    #model = MNIST_MLP().to(device)
    model = MNIST_CNN().to(device)
    print(model)
    #logger = Logger('./logs')
    train_logger = TensorBoard('./logs/train')
    valid_logger = TensorBoard('./logs/valid')
    # Loss and optimizer
    criterion = nn.CrossEntropyLoss()  
    optimizer = torch.optim.Adam(model.parameters(), lr=learning_rate)  

    #data_iter = iter(train_loader)
    #iter_per_epoch = len(train_loader)
    #total_step = 5000
    for epoch in range(num_epochs):
        train(train_loader, epoch, model, criterion, optimizer, train_logger)
        valid(valid_loader, epoch, model, criterion, valid_logger)

def train(train_loader, epoch, model, criterion, optimizer, train_logger):
    model.train()
    iter_per_epoch = len(train_loader)
    # Start training
    for iter, (images, labels) in enumerate(train_loader):
        #images = images.view(images.size(0), -1)
        images = images.to(device)
        labels = labels.to(device)
        # Forward pass
        outputs = model(images)
        loss = criterion(outputs, labels)

        # Backward and optimize
        optimizer.zero_grad()
        loss.backward()
        optimizer.step()

        # Compute accuracy
        _, argmax = torch.max(outputs, 1)
        accuracy = (labels == argmax.squeeze()).float().mean()
        
        if (iter+1) % 100 == 0:
            torch.save(model.state_dict(), 'model_epoch{}_iteration{}.ckpt'.format(epoch,iter+1))
            print ('Epoch {}, Iteration [{}/{}], Loss: {:.4f}, Acc: {:.2f}' 
                   .format(epoch, iter+1, iter_per_epoch, loss.item(), accuracy.item()))
            # ================================================================== #
            #                        Tensorboard Logging                         #
            # ================================================================== #
            train_logger.log_scalar('traing loss', loss.item(), epoch * iter_per_epoch + iter+1)
            train_logger.log_scalar('training accuracy', accuracy.item(), epoch * iter_per_epoch + iter+1)
        
def valid(valid_loader, epoch, model, criterion, valid_logger):
    model.eval()
    iter_per_epoch = len(valid_loader)
    
    for iter, (images, labels) in enumerate(valid_loader):
        #images = images.view(images.size(0), -1)
        images = images.to(device)
        labels = labels.to(device)
        outputs = model(images)
        loss = criterion(outputs, labels)

        # Compute validate accuracy and tensorboard logging
        if (iter+1) % 100 == 0:
            _, argmax = torch.max(outputs, 1)
            accuracy = (labels == argmax.squeeze()).float().mean() 
            print('Epoch {}, Iteration [{}/{}], Loss: {:.4f}, Validate Accuracy: {:.2f}'
                  .format(epoch, iter+1, iter_per_epoch, loss.item(), accuracy.item()))
            valid_logger.log_scalar('validation loss', loss.item(), epoch * iter_per_epoch+iter+1)
            valid_logger.log_scalar('validation accuracy', accuracy.item(), epoch*iter_per_epoch+iter+1)

if __name__ == '__main__':
    main()
