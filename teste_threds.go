package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

// Função que simula o pico de estresse na CPU a cada 5 segundos
func picoDeEstresse() {
	for {
		// A cada 5 segundos, simula um pico de estresse
		time.Sleep(5 * time.Second)
		fmt.Println("Pico de estresse iniciado!")

		// Duração aleatória para o pico de estresse (de 0.5 a 2 segundos)
		duracaoPico := time.Duration(rand.Float64()*1.5+0.5) * time.Second
		fmt.Printf("Pico de estresse durando %.2f segundos\n", duracaoPico.Seconds())

		// Simula a sobrecarga de CPU (busy-wait)
		start := time.Now()
		for time.Since(start) < duracaoPico {
			// O processo só fica ocupado aqui, consumindo CPU
		}

		fmt.Println("Pico de estresse finalizado!")
	}
}

// Função que simula uma tarefa pesada
func tarefaPesada(n int) {
	for {
		fmt.Printf("Iniciando tarefa pesada %d\n", n)
		time.Sleep(time.Duration(rand.Intn(2000)+1000) * time.Millisecond) // Tarefa pesada (1 a 3 segundos)
		fmt.Printf("Finalizando tarefa pesada %d\n", n)
	}
}

// Função que simula uma tarefa leve
func tarefaLeve(n int) {
	for {
		fmt.Printf("Iniciando tarefa leve %d\n", n)
		time.Sleep(time.Duration(rand.Intn(400)+100) * time.Millisecond) // Tarefa leve (0.1 a 0.5 segundos)
		fmt.Printf("Finalizando tarefa leve %d\n", n)
	}
}

func main() {
	// Cria um WaitGroup para aguardar as goroutines terminarem
	var wg sync.WaitGroup

	// Inicia o pico de estresse em uma goroutine
	wg.Add(1)
	go func() {
		defer wg.Done()
		picoDeEstresse()
	}()

	// Inicia as tarefas pesadas (10 tarefas)
	for i := 0; i < 10; i++ {
		wg.Add(1)
		go func(n int) {
			defer wg.Done()
			tarefaPesada(n)
		}(i)
	}

	// Inicia as tarefas leves (6 tarefas)
	for i := 0; i < 6; i++ {
		wg.Add(1)
		go func(n int) {
			defer wg.Done()
			tarefaLeve(n)
		}(i)
	}

	// O programa ficará rodando indefinidamente, então usamos um wait no WaitGroup para manter o programa ativo.
	wg.Wait() // Espera todas as goroutines terminarem (na prática, elas nunca terminam)
}
