---
name: creating-skills
description: Genera skills de Cursor con estructura estandarizada. Usar cuando el usuario menciona crear skill, nueva habilidad, generar skill, scaffold de capacidad para agente, o agregar habilidad al workspace.
---

# Creando Skills para Cursor

Skill generador que produce directorios `.cursor/skills/` de alta calidad, predecibles y eficientes.

## Cuando usar esta habilidad

- El usuario menciona "crear skill", "nueva habilidad", "generar skill"
- Se solicita "scaffold de capacidad para agente"
- Se pide "agregar habilidad al workspace"
- Cualquier referencia a extender las capacidades del agente de Cursor

## Flujo de trabajo

### Lista de verificacion

```markdown
- [ ] 1. Definir nombre del skill (gerundio, kebab-case, max 64 chars)
- [ ] 2. Escribir descripcion (tercera persona, con triggers, max 1024 chars)
- [ ] 3. Crear estructura de carpetas
- [ ] 4. Escribir logica en SKILL.md
- [ ] 5. Agregar scripts/ si se necesitan automatizaciones
- [ ] 6. Agregar examples/ si se requieren referencias
- [ ] 7. Agregar resources/ si hay plantillas o assets
- [ ] 8. Validar que SKILL.md tenga menos de 500 lineas
```

### Patron Planificar-Validar-Ejecutar

1. **Planificar**: Definir proposito, triggers y flujo del skill
2. **Validar**: Verificar nomenclatura y estructura antes de crear archivos
3. **Ejecutar**: Crear archivos siguiendo la plantilla estandar

## Estructura requerida

```
.cursor/skills/
└── <nombre-del-skill>/
    ├── SKILL.md           # Obligatorio: Logica principal
    ├── scripts/           # Opcional: Scripts de ayuda
    ├── examples/          # Opcional: Implementaciones de referencia
    └── resources/         # Opcional: Plantillas o recursos
```

## Reglas de nomenclatura

### Nombre del skill

- **Formato**: Gerundio en kebab-case (ej: `testing-code`, `managing-databases`)
- **Longitud**: Maximo 64 caracteres
- **Caracteres permitidos**: Solo minusculas, numeros y guiones
- **Restricciones**: No usar "claude" ni "anthropic" en el nombre

### Descripcion del skill

- **Persona**: Escrita en tercera persona
- **Contenido**: Debe incluir triggers/palabras clave especificas
- **Longitud**: Maximo 1024 caracteres
- **Ejemplo**: "Extrae texto de PDF. Usar cuando el usuario menciona procesamiento de documentos o archivos PDF."

## Grados de libertad

Seleccionar el formato segun la rigidez de la tarea:

| Tipo de tarea | Formato | Uso |
|---------------|---------|-----|
| Alta libertad (heuristicas) | Vinetas | Decisiones que el agente puede tomar |
| Media libertad (plantillas) | Bloques de codigo | Estructuras con variaciones |
| Baja libertad (operaciones fragiles) | Comandos Bash especificos | Operaciones criticas exactas |

## Plantilla de salida

Al generar un skill, usar esta estructura:

```markdown
---
name: [nombre-en-gerundio]
description: [descripcion en tercera persona con triggers]
---

# [Titulo de la Habilidad]

## Cuando usar esta habilidad
- [Disparador 1]
- [Disparador 2]

## Flujo de trabajo
[Lista de verificacion en formato Markdown]

## Instrucciones
[Logica especifica, fragmentos de codigo o reglas]

## Recursos
- [Enlaces a scripts/ o resources/ si aplica]
```

## Manejo de errores

- Si un script falla, ejecutar `--help` para obtener informacion de uso
- Los scripts deben tratarse como "cajas negras" - no asumir comportamiento interno
- Documentar errores comunes y soluciones en el SKILL.md

## Principios de escritura

- **Concision**: Asumir que el agente es inteligente. No explicar conceptos basicos.
- **Divulgacion progresiva**: Mantener SKILL.md bajo 500 lineas. Enlazar a archivos secundarios si se necesita mas detalle (solo un nivel de profundidad).
- **Rutas**: Siempre usar `/` para rutas, nunca `\`.

## Recursos

- [Plantilla base para nuevos skills](resources/SKILL-TEMPLATE.md)
- [Ejemplo de skill funcional](examples/example-skill/SKILL.md)
