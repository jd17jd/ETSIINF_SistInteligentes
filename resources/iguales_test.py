import pandas as pd
from sklearn.model_selection import train_test_split

# Cargar el dataset
df = pd.read_csv('resources/csv_result-diabetes.csv')

# Mostrar los nombres de las columnas para verificar
print(df.columns)

# Limpiar los nombres de las columnas eliminando comillas adicionales
df.columns = df.columns.str.replace("'", "").str.strip()

# Verificar nuevamente
print(df.columns)

# Dividir el dataset en instancias positivas y negativas
positivos = df[df['label'] == 1]
negativos = df[df['label'] == 0]

# Determinar el tamaño del conjunto de prueba balanceado
num_test_instances = int(0.1 * len(df))  # 10% del total de datos
num_test_per_class = num_test_instances // 2  # Mitad positivas, mitad negativas

# Seleccionar muestras aleatorias para el conjunto de prueba
test_positivos = positivos.sample(n=num_test_per_class, random_state=42)
test_negativos = negativos.sample(n=num_test_per_class, random_state=42)

# Combinar las muestras seleccionadas para formar el conjunto de prueba
test_set = pd.concat([test_positivos, test_negativos])

# Eliminar las instancias del conjunto de prueba del dataset original para obtener el conjunto de entrenamiento
train_set = df.drop(test_set.index)

# Guardar los conjuntos de entrenamiento y prueba en archivos CSV separados
train_set.to_csv('train_set.csv', index=False)
test_set.to_csv('test_set.csv', index=False)

# Verificar la división
print(f'Tamaño del conjunto de entrenamiento: {len(train_set)}')
print(f'Tamaño del conjunto de prueba: {len(test_set)}')
print(f'Positivos en el conjunto de prueba: {test_set[test_set["label"] == 1].shape[0]}')
print(f'Negativos en el conjunto de prueba: {test_set[test_set["label"] == 0].shape[0]}')
