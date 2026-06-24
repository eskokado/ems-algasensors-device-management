# Device Management Service

O **Device Management Service** é o microserviço responsável por manter o inventário de dispositivos e sensores do ecossistema AlgaSensors.

## 📝 Responsabilidades

- Cadastro de novos dispositivos de medição.
- Gerenciamento de metadados dos sensores.
- Fornecimento de uma base unificada para identificação de sensores (via TSID).

## 🔧 Detalhes Técnicos

- **Framework**: Spring Boot 3.4.0
- **Linguagem**: Java 21
- **Porta**: 8080
- **Armazenamento**: H2 Database (Persistent file)
- **ID Strategy**: TSID (Time-Sorted Unique Identifiers)

### Principais Componentes

- `Sensor`: Entidade de domínio que representa um dispositivo físico.
- `SensorRepository`: Interface para persistência e recuperação de sensores.
- `IdGenerator`: Utilitário para geração de identificadores TSID.

## 🚀 Execução

```bash
./gradlew bootRun
```

A base de dados H2 pode ser acessada em tempo de execução via console no endpoint `/h2-console` (se habilitado).
