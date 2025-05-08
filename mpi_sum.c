// mpi_sum.c
#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>

int main(int argc, char *argv[]) {
    int rank, size;
    int N = 100;  // Number of elements in the array
    int n = 10;   // Number of processors (you can adjust this)
    int arr[N];
    int local_sum = 0, total_sum = 0;
    int i;

    // Initialize MPI environment
    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    // Initialize array only on rank 0
    if (rank == 0) {
        // Create a simple array with values 1 to 100
        for (i = 0; i < N; i++) {
            arr[i] = i + 1;  // Array: [1, 2, 3, ..., 100]
        }
    }

    // Divide the work among processes
    int elements_per_process = N / size;
    int *sub_arr = (int *)malloc(elements_per_process * sizeof(int));

    // Scatter the data to all processes
    MPI_Scatter(arr, elements_per_process, MPI_INT, sub_arr, elements_per_process, MPI_INT, 0, MPI_COMM_WORLD);

    // Each process computes its local sum
    for (i = 0; i < elements_per_process; i++) {
        local_sum += sub_arr[i];
    }

    // Print intermediate sum from each processor
    printf("Processor %d calculated local sum: %d\n", rank, local_sum);

    // Reduce all local sums to compute the global sum (total_sum)
    MPI_Reduce(&local_sum, &total_sum, 1, MPI_INT, MPI_SUM, 0, MPI_COMM_WORLD);

    // Rank 0 process will print the final result
    if (rank == 0) {
        printf("Total sum: %d\n", total_sum);
    }

    // Free memory
    free(sub_arr);

    // Finalize MPI environment
    MPI_Finalize();

    return 0;
}
