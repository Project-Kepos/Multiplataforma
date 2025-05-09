# 🌿 Front-End Multiplataforma do Kêpos – Sistema de Gerenciamento de Estufa (IoT + Kotlin Multiplatform)

Este repositório contém o **front-end multiplataforma do Kêpos**, um sistema para gerenciamento automatizado de estufas utilizando **Internet das Coisas (IoT)**.  
O objetivo do projeto é facilitar o controle e monitoramento de variáveis como **temperatura**, **umidade** e **iluminação** — promovendo um cultivo mais eficiente e sustentável.

A interface foi desenvolvida com **Kotlin Multiplatform** e **Compose Multiplatform**, com a intenção de disponibilizar o Kêpos em diversas plataformas, como:

- 📱 **Android**  
- 🌐 **Web**  
- 💻 **Desktop**

---

## 🔗 Repositórios Relacionados

- 🔧 [Back-End (Spring Boot)](https://github.com/Project-Kepos/Kepos-2.0-Back-End)
- 🌱 [IoT (Código para microcontroladores)](https://github.com/Project-Kepos/kepos-dendro-code)
- 🖥️ [Front-End (Versão Web em React)](https://github.com/Project-Kepos/kepos-front-end)

---

## 🛠️ Estrutura do Projeto

O front-end está organizado no módulo `/composeApp`, com a seguinte estrutura:

- `commonMain`: código compartilhado entre todas as plataformas.
- `androidMain`, `desktopMain`, `wasmJsMain`: código específico para cada plataforma.

---

## 📚 Recursos e Links Úteis

- [📘 Kotlin Multiplatform – Documentação oficial](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)  
- [🧩 Compose Multiplatform – GitHub](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform)  
- [⚙️ Kotlin/Wasm](https://kotl.in/wasm/)  
- [💬 Canal público no Slack: #compose-web](https://slack-chats.kotlinlang.org/c/compose-web)  
- [🐛 Relate problemas ou contribua – GitHub Issues](https://github.com/JetBrains/compose-multiplatform/issues)

---

## 🚀 Como rodar a aplicação web

Execute a seguinte task Gradle no terminal:

```bash
:composeApp:wasmJsBrowserDevelopmentRun
