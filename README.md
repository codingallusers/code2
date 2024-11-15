# necessary package
import tensorflow as tf
from tensorflow import keras
import matplotlib.pyplot as plt
import random


# load the training and testing data
mnist = tf.keras.datasets.mnist
(x_train, y_train), (x_test, y_test) = mnist.load_data()


# Check the Dataset Size and Structure:
# Size of training and testing datasets
len(x_train)  # Output: 60000


# Size of training and testing datasets
len(x_test)   # Output: 10000


# Shape of the datasets
x_train.shape  # (60000, 28, 28)


# Shape of the datasets
x_test.shape   # (10000, 28, 28)


# Display a sample image from the training data
plt.matshow(x_train[0])


# To ensure consistent learning, we normalize the pixel values from their original range (0–255) to 0–1.
# Normalize pixel values to the range [0, 1]
x_train = x_train / 255
x_test = x_test / 255


# define the network architecture using keras
model = keras.Sequential([
keras.layers.Flatten(input_shape=(28,28)),
keras.layers.Dense(128, activation="relu"),
keras.layers.Dense(10, activation="softmax")
])


# train the model using SGD
model.compile(optimizer="sgd",
loss = "sparse_categorical_crossentropy",
metrics=['accuracy'])


history=model.fit(x_train,
y_train,validation_data=(x_test, y_test),epochs=3)



# evaluate the network
test_loss,test_acc= model.evaluate(x_test,y_test)
print("loss=%.3f" %test_loss)
print("Accuracy=%.3f" %test_acc)
n=random.randint(0,9999)
plt.imshow(x_test[n])
plt.show()
predicted_value=model.predict(x_test)
plt.imshow(x_test[n])
plt.show()


print('Predicted Value: ', predicted_value[n])


# plot the training loss and accuracy
# plot the training accuracy
plt.plot(history.history['accuracy'])
plt.plot(history.history['val_accuracy'])
plt.title('model accuracy')
plt.ylabel('accuracy')
plt.xlabel('epoch')
plt.legend(['Train', 'Validation'], loc='upper right')
plt.show()


#plot the training loss
plt.plot(history.history['loss'])
plt.plot(history.history['val_loss'])
plt.title('model loss')
plt.ylabel('loss')
plt.xlabel('epoch')
plt.legend(['Train', 'Validation'], loc='upper left')
plt.show()
