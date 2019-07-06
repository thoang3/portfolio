import io
import numpy as np
from PIL import Image
import tensorflow as tf


class TensorBoard:
    def __init__(self, logdir):
        self.writer = tf.summary.FileWriter(logdir)

    def close(self):
        self.writer.close()

    def log_scalar(self, tag, value, global_step):
        summary = tf.Summary()
        summary.value.add(tag=tag, simple_value=value)
        self.writer.add_summary(summary, global_step=global_step)
        self.writer.flush()


def main():
    tensorboard = TensorBoard('log')

    x = np.arange(1, 101)
    y = 20 + 3 * x + np.random.random(100) * 100
    
    import time
    # Log simple values
    for i in range(0, 100):
        time.sleep(1)
        print(i)
        tensorboard.log_scalar('value', y[i], i)

