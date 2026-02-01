---
name: formatting-code
description: Formatea codigo fuente segun los estandares del proyecto. Usar cuando el usuario menciona formatear, limpiar codigo, arreglar indentacion, o aplicar estilo de codigo.
---

# Formateando Codigo

Skill para aplicar formateo consistente al codigo fuente del proyecto.

## Cuando usar esta habilidad

- El usuario menciona "formatear codigo" o "limpiar codigo"
- Se solicita "arreglar indentacion" o "aplicar estilo"
- Se pide "hacer el codigo mas legible"
- Antes de hacer commit de cambios

## Flujo de trabajo

### Lista de verificacion

```markdown
- [ ] 1. Identificar tipo de archivo (lenguaje)
- [ ] 2. Detectar configuracion de formateo del proyecto
- [ ] 3. Ejecutar formateador apropiado
- [ ] 4. Verificar que no se introdujeron errores
- [ ] 5. Reportar cambios realizados
```

### Patron de ejecucion

1. **Planificar**: Detectar archivos a formatear y reglas aplicables
2. **Validar**: Verificar que existe configuracion de formateo
3. **Ejecutar**: Aplicar formateo y reportar resultados

## Instrucciones

### Detectar formateador por lenguaje

| Lenguaje | Formateador | Config |
|----------|-------------|--------|
| JavaScript/TypeScript | Prettier | `.prettierrc` |
| Python | Black/Ruff | `pyproject.toml` |
| Go | gofmt | Integrado |
| Rust | rustfmt | `rustfmt.toml` |

### Comandos por ecosistema

```bash
# JavaScript/TypeScript
npx prettier --write "src/**/*.{js,ts,tsx}"

# Python
ruff format .

# Go
gofmt -w .
```

### Reglas principales

- Siempre verificar si existe configuracion del proyecto antes de usar defaults
- No formatear archivos generados automaticamente
- Respetar archivos de ignore (`.prettierignore`, etc.)

## Manejo de errores

| Error | Causa | Solucion |
|-------|-------|----------|
| `Prettier not found` | No instalado | `npm install -D prettier` |
| `Config parse error` | Archivo de config invalido | Verificar sintaxis JSON/YAML |
| `No files matched` | Patron incorrecto | Verificar glob pattern |

## Recursos

- Configuracion se detecta automaticamente del proyecto
