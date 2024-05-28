import pandas as pd
from sklearn.model_selection import train_test_split

def dividir_y_guardar_dataset(file_path, train_output_path, test_output_path, test_size=0.1, random_state=42):
    # Cargar el dataset
    df = pd.read_csv(file_path)

    # Limpiar los nombres de las columnas eliminando comillas adicionales
    df.columns = df.columns.str.replace("'", "").str.strip()

    # Dividir el dataset en instancias positivas y negativas
    positivos = df[df['label'] == 1]
    negativos = df[df['label'] == 0]

    # Determinar el tamaño del conjunto de prueba balanceado
    num_test_instances = int(test_size * len(df))  # test_size porcentaje del total de datos
    num_test_per_class = num_test_instances // 2  # Mitad positivas, mitad negativas

    # Seleccionar muestras aleatorias para el conjunto de prueba
    test_positivos = positivos.sample(n=num_test_per_class, random_state=random_state)
    test_negativos = negativos.sample(n=num_test_per_class, random_state=random_state)

    # Combinar las muestras seleccionadas para formar el conjunto de prueba
    test_set = pd.concat([test_positivos, test_negativos])

    # Eliminar las instancias del conjunto de prueba del dataset original para obtener el conjunto de entrenamiento
    train_set = df.drop(test_set.index)

    # Reemplazar los valores de la columna 'label'
    train_set['label'] = train_set['label'].replace({1: 'tested_negative', 0: 'tested_positive'})
    test_set['label'] = test_set['label'].replace({1: 'tested_negative', 0: 'tested_positive'})

    # Eliminar el primer campo de cada línea del CSV
    train_set = train_set.drop(columns=train_set.columns[0])
    test_set = test_set.drop(columns=test_set.columns[0])

    # Agregar líneas de metadatos y atributos al inicio del documento
    metadata = '''@relation pima_diabetes
@attribute 'preg' real
@attribute 'plas' real
@attribute 'pres' real
@attribute 'skin' real
@attribute 'insu' real
@attribute 'mass' real
@attribute 'pedi' real
@attribute 'age' real
@attribute 'class' {tested_negative, tested_positive}
@data
'''
    # Guardar los conjuntos de entrenamiento y prueba en archivos CSV separados
    with open(train_output_path, 'w') as train_file:
        train_file.write(metadata)
        train_set.to_csv(train_file, index=False)

    with open(test_output_path, 'w') as test_file:
        test_file.write(metadata)
        test_set.to_csv(test_file, index=False)

    # Verificar la división
    print(f'Tamaño del conjunto de entrenamiento: {len(train_set)}')
    print(f'Tamaño del conjunto de prueba: {len(test_set)}')
    print(f'Positivos en el conjunto de prueba: {test_set[test_set["label"] == "tested_positive"].shape[0]}')
    print(f'Negativos en el conjunto de prueba: {test_set[test_set["label"] == "tested_negative"].shape[0]}')

# Uso de la función
dividir_y_guardar_dataset('resources/csv_result-diabetes.csv', 'diabetes-train.arff', 'diabetes-test.arff')


