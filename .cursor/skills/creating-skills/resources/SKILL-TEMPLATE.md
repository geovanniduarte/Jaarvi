---
name: {{SKILL_NAME}}
description: {{SKILL_DESCRIPTION}}
---

# {{SKILL_TITLE}}

## Cuando usar esta habilidad

- {{TRIGGER_1}}
- {{TRIGGER_2}}
- {{TRIGGER_3}}

## Flujo de trabajo

### Lista de verificacion

```markdown
- [ ] 1. {{STEP_1}}
- [ ] 2. {{STEP_2}}
- [ ] 3. {{STEP_3}}
- [ ] 4. {{STEP_4}}
```

### Patron de ejecucion

1. **Planificar**: {{PLAN_DESCRIPTION}}
2. **Validar**: {{VALIDATE_DESCRIPTION}}
3. **Ejecutar**: {{EXECUTE_DESCRIPTION}}

## Instrucciones

### Reglas principales

- {{RULE_1}}
- {{RULE_2}}
- {{RULE_3}}

### Comandos disponibles

```bash
# {{COMMAND_DESCRIPTION}}
{{COMMAND}}
```

## Manejo de errores

| Error | Causa | Solucion |
|-------|-------|----------|
| {{ERROR_1}} | {{CAUSE_1}} | {{SOLUTION_1}} |
| {{ERROR_2}} | {{CAUSE_2}} | {{SOLUTION_2}} |

## Recursos

- [{{RESOURCE_NAME}}]({{RESOURCE_PATH}})

---

## Marcadores a reemplazar

| Marcador | Descripcion | Ejemplo |
|----------|-------------|---------|
| `{{SKILL_NAME}}` | Nombre en gerundio, kebab-case | `testing-code` |
| `{{SKILL_DESCRIPTION}}` | Descripcion en tercera persona | `Ejecuta tests automatizados...` |
| `{{SKILL_TITLE}}` | Titulo legible del skill | `Testing de Codigo` |
| `{{TRIGGER_N}}` | Frases que activan el skill | `ejecutar tests`, `correr pruebas` |
| `{{STEP_N}}` | Pasos del flujo de trabajo | `Identificar archivos de test` |
| `{{PLAN_DESCRIPTION}}` | Descripcion de fase de planificacion | `Analizar estructura del proyecto` |
| `{{VALIDATE_DESCRIPTION}}` | Descripcion de fase de validacion | `Verificar configuracion de tests` |
| `{{EXECUTE_DESCRIPTION}}` | Descripcion de fase de ejecucion | `Ejecutar suite de tests` |
| `{{RULE_N}}` | Reglas o instrucciones especificas | `Usar pytest para Python` |
| `{{COMMAND}}` | Comando bash a ejecutar | `npm test` |
| `{{ERROR_N}}` | Tipo de error | `Test timeout` |
| `{{CAUSE_N}}` | Causa del error | `Test asincrono sin await` |
| `{{SOLUTION_N}}` | Como resolver el error | `Agregar async/await` |
| `{{RESOURCE_NAME}}` | Nombre del recurso | `Configuracion de Jest` |
| `{{RESOURCE_PATH}}` | Ruta al recurso | `resources/jest.config.js` |
