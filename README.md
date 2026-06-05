# Computational Intelligence — Laboratory Assignments (2024-2025) 🧠

This repository contains the complete implementations and reports for the laboratory projects of the **Computational Intelligence** university course. The algorithms are built completely from scratch, without the use of high-level machine learning libraries.

---

## 📁 Repository Structure

The project is organized into two primary directories, each containing the source code, executables, and analysis reports.

### 📊 Assignment 1: Multi-Layer Perceptron (MLP) Classifier
Implementation of neural network classifiers optimized for a complex 4-class geometric classification problem containing 8,000 generated datasets.
* **MLP Architectures:** Custom structures featuring both 2 hidden layers (MLP2) and 3 hidden layers (MLP3).
* **Core Components:** Fully vectorized Forward-Pass, Backpropagation algorithm for error derivative tracking, and Mini-Batch Gradient Descent optimization.
* **Hyperparameter Tuning:** Empirical study on hidden unit dimensions ($H_1, H_2, H_3$), activation functions ($\tanh$, $\text{ReLU}$), and batch sizes ($B = N/20$ or $B = N/200$).
* **Directory:** `/01-mlp-classification`

### 📍 Assignment 2: K-Means Clustering Engine
Implementation of the $k$-means clustering unsupervised learning algorithm applied to an 8-cluster spatial dataset (1,000 samples).
* **Core Components:** Multi-run coordination (20 independent runs with randomized initial centroids) to bypass local minima and prevent suboptimal clustering.
* **Evaluation:** Sum of Squared Errors (SSE) calculation utilizing Euclidean distance metrics to detect the optimal elbow point for cluster estimation.
* **Directory:** `/02-kmeans-clustering`

---

## 💻 Tech Stack & Environment
* **Programming Languages:** Java / C (No automated ML libraries used)
* **Mathematical Operations:** Low-level matrix manipulations, standard Euclidean spaces, and custom activation functions.

---

## 🚀 Compilation & Execution

Each folder contains specific compilation scripts. Since the projects are written in pure C/Java, they can be compiled on standard university terminals using the following blueprints:

### Compiling the MLP Classifier (example in C):
```bash
gcc -O3 main.py_or_c.c -o mlp_classifier -lm
./mlp_classifier
