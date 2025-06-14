import multiprocessing
import time
import random
import threading

def pico_de_estresse():
    """Simula um pico de estresse aleatório a cada 5 segundos em loop infinito."""
    while True:
        time.sleep(5)  # Espera 5 segundos
        print("Pico de estresse iniciado!")
        
        # Criando um "pico de estresse" aumentando a carga por um tempo
        duracao_pico = random.uniform(0.5, 2.0)  # Duração aleatória do pico de estresse
        print(f"Pico de estresse durando {duracao_pico:.2f} segundos")
        # Simula o aumento de carga de CPU por essa duração
        start = time.time()
        while time.time() - start < duracao_pico:
            pass  # Realiza uma operação de "busy-wait" para estressar a CPU

        print("Pico de estresse finalizado!")

def tarefa_pesada(n):
    while True:  # Loop infinito para as tarefas pesadas
        print(f"Iniciando tarefa pesada {n}")
        time.sleep(random.uniform(1, 3))  # Simula uma tarefa pesada
        print(f"Finalizando tarefa pesada {n}")

def tarefa_leve(n):
    while True:  # Loop infinito para as tarefas leves
        print(f"Iniciando tarefa leve {n}")
        time.sleep(random.uniform(0.1, 0.5))  # Simula uma tarefa leve
        print(f"Finalizando tarefa leve {n}")

def main():
    num_nucleos = multiprocessing.cpu_count()  # Descobre o número de núcleos disponíveis
    tarefas_pesadas = [multiprocessing.Process(target=tarefa_pesada, args=(i,)) for i in range(10)]
    tarefas_leves = [multiprocessing.Process(target=tarefa_leve, args=(i,)) for i in range(6)]
    
    # Inicia o pico de estresse em um thread separado
    stress_thread = threading.Thread(target=pico_de_estresse, daemon=False)
    stress_thread.start()

    # Inicia todas as tarefas pesadas (usando 4 núcleos, por exemplo)
    for i in range(4):  # Distribui as 4 primeiras tarefas pesadas nos núcleos
        tarefas_pesadas[i].start()

    # Inicia as tarefas leves, aproveitando os núcleos ociosos
    for i in range(6):  # Começa as tarefas leves
        tarefas_leves[i].start()

    # Espera as tarefas pesadas restantes terminarem (na verdade, elas vão rodar infinitamente)
    for i in range(4, 10):
        tarefas_pesadas[i].start()

    # Não chamamos join() para as tarefas, pois elas agora são infinitas.
    # O processo principal continuará rodando infinitamente até que o programa seja interrompido manualmente.
    while True:
        time.sleep(1)  # Manter o loop do processo principal ativo.

def pico_de_estresse():

if __name__ == "__main__":
    main()
