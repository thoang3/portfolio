import torch
import torch.nn as nn
import torchvision
import torchvision.transforms as transforms
from tensorboard import TensorBoard
from resnet import ResNet, ResidualBlock

# Device configuration
device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')


def main():
    # Hyperparameters
    num_epochs = 80
    batch_size = 100
    learning_rate = 0.001

    # Image preprocessing/ Data augmentation
    transform = transforms.Compose([
        transforms.Pad(4),
        transforms.RandomHorizontalFlip(),
        transforms.RandomCrop(32),
        transforms.ToTensor()])

    # CIFAR-10 dataset
    train_dataset = torchvision.datasets.CIFAR10(root='/mnt/cifar/',
                                                 train=True,
                                                 transform=transform,
                                                 download=True)
    valid_dataset = torchvision.datasets.CIFAR10(root='/mnt/cifar/',
                                                train=False,
                                                transform=transforms.ToTensor())

    # Data loader
    train_loader = torch.utils.data.DataLoader(dataset=train_dataset,
                                               batch_size=batch_size,
                                               shuffle=True)
    valid_loader = torch.utils.data.DataLoader(dataset=valid_dataset,
                                               batch_size=batch_size,
                                               shuffle=False)
    print(len(train_loader))
    print(len(valid_loader))

    model = ResNet(ResidualBlock, [2, 2, 2]).to(device)
    print(model)

    # Loss and optimizer
    criterion = nn.CrossEntropyLoss()
    optimizer = torch.optim.Adam(model.parameters(), lr=learning_rate)

    # Tensorboard logging
    train_logger = TensorBoard('./logs/train')
    valid_logger = TensorBoard('./logs/valid')

    # For updating learning rate
    def update_lr(optimizer, lr):
        for param_group in optimizer.param_groups:
            param_group['lr'] = lr
    current_lr = learning_rate

    for epoch in range(num_epochs):
        train(train_loader, epoch, num_epochs, model, criterion, optimizer, train_logger)
        valid(valid_loader, epoch, num_epochs, model, criterion, valid_logger)
        
        # Decay learning rate
        if (epoch+1) % 20 == 0:
            current_lr /= 3
            update_lr(optimizer, current_lr)


def train(train_loader, epoch, num_epochs, model, criterion, optimizer, train_logger):
    model.train()
    iter_per_epoch = len(train_loader)
    # Start training
    for iter, (images, labels) in enumerate(train_loader):
        images = images.to(device)
        labels = labels.to(device)
        # Forward pass
        outputs = model(images)
        loss = criterion(outputs, labels)
        # Backward and optimize
        optimizer.zero_grad()
        loss.backward()
        optimizer.step()

        if (iter+1) % 100 == 0:
            _, argmax = torch.max(outputs, 1)
            accuracy = (labels == argmax.squeeze()).float().mean()
            print('Epoch [{}/{}], Iteration [{}/{}], Loss {:.4f}, Training Accuracy {:.2f}'
                  .format(epoch+1, num_epochs, iter+1, iter_per_epoch, loss.item(), accuracy.item()))
            # Tensorboard logging
            train_logger.log_scalar('training loss', loss.item(), epoch * iter_per_epoch + iter)
            train_logger.log_scalar('training accuracy', accuracy.item(), epoch * iter_per_epoch + iter)
    torch.save(model.state_dict(), 'checkpoints/cifar_resnet_epoch_{}.ckpt'.format(epoch+1))

def valid(valid_loader, epoch, num_epochs, model, criterion, valid_logger):
    model.eval()
    iter_per_epoch = len(valid_loader)
    with torch.no_grad():
        correct = 0
        total = 0
        for iter, (images, labels) in enumerate(valid_loader):
            images = images.to(device)
            labels = labels.to(device)
            outputs = model(images)
            loss = criterion(outputs, labels)

            _, predicted = torch.max(outputs.data, 1)
            total += labels.size(0)
            correct += (predicted == labels).sum().item()

            # Compute validate accuracy and tensorboard logging
            if (iter+1) % 100 == 0:
                _, argmax = torch.max(outputs, 1)
                accuracy = (labels == argmax.squeeze()).float().mean()
                print('Epoch [{}/{}], Iteration [{}/{}], Loss: {:.4f}, Validate Accuracy: {:.2f}'
                  .format(epoch+1, num_epochs, iter+1, iter_per_epoch, loss.item(), accuracy.item()))
                valid_logger.log_scalar('validation loss', loss.item(), epoch * iter_per_epoch + iter)
                valid_logger.log_scalar('validation accuracy', accuracy.item(), epoch * iter_per_epoch + iter)
        print('Validation accuracy is: {} %'.format(100 * correct / total))

if __name__ == '__main__':
    main()
