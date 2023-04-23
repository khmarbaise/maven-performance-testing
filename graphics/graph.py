import pandas as pd
import matplotlib.pyplot as plt

# Replace this with the path to your JSON file
json_file_path = 'json/results-8.0.362-tem-4000.json'

# Read the JSON file into a pandas DataFrame
data = pd.read_json(json_file_path)

# Assuming your JSON file has 'x' and 'y' keys for the measurement data
x = data['x']
y = data['y']

# Plot the data
plt.plot(x, y)

# Customize your graph with labels, titles, and more
plt.xlabel('X-axis label')
plt.ylabel('Y-axis label')
plt.title('Your graph title')

# Show the graph
plt.show()

