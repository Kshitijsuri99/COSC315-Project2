#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>
#include <time.h>
#include <string.h>

int c=0;
sem_t empty, full;
#define size 5 
int buffer[size];
pthread_mutex_t mutex;
time_t start;
time_t end;
long overallTime;
long consumerOverallTime;

void *producer(void *argv){
    int random;
    int randomSleep;
    for(int i = 0; i < size; i++) {
        randomSleep = rand() % 10;
        random = rand();
        start = time(NULL);
        sem_wait(&empty);
        sleep(randomSleep);
        pthread_mutex_lock(&mutex);
        buffer[c] = random;
        c=c+1;
        pthread_mutex_unlock(&mutex);
        sem_post(&full);
        end = time(NULL);
        overallTime = difftime(end,start);
    }
}

void *consumer(void *argv){
    int value;
    int randomSleep;
    for(int i = 0; i < size; i++) {
        randomSleep = rand()%10;
        start = time(NULL);
        sem_wait(&full);
        sleep(randomSleep);
        pthread_mutex_lock(&mutex);
        value = buffer[c];
        buffer[c] = 0;
        c=c-1;
        int consumerID = i+1;
        time_t cTime = time(NULL);
        char *currentTime = ctime(&cTime);
        currentTime[strlen(currentTime)-1] = '\0';
        printf("Consumer %d: assigned request ID %d, processing request for the next %d seconds, current time is %s\n", consumerID, value, overallTime, currentTime);
        pthread_mutex_unlock(&mutex);
        sem_post(&empty);
        end = time(NULL);
        consumerOverallTime = difftime(end,start);
        char *currentConsumerTime = ctime(&cTime);
        currentConsumerTime[strlen(currentConsumerTime)-1] = '\0';
        printf("Consumer %d: completed requst ID %d at time %s\n",consumerID, value, currentConsumerTime);
    }
}

int main()
{
 	pthread_t producers;
    	pthread_t consumers;
	pthread_mutex_init(&mutex, NULL);
	sem_init(&empty, 0, size);
	sem_init(&full, 0, 0);
	pthread_create(&producers, NULL, (void *)producer, NULL);
    	pthread_join(producers, NULL);
	pthread_create(&consumers, NULL, (void *)consumer, NULL);
	pthread_join(consumers, NULL);
}
