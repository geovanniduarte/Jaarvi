FROM node:20-slim AS deps
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm ci

# Generate Prisma client during image build so runtime does not need Prisma CLI.
COPY prisma ./prisma
RUN npx prisma generate

FROM node:20-slim AS build
WORKDIR /app
COPY --from=deps /app/node_modules ./node_modules
COPY . .
RUN npm run build

FROM node:20-slim AS runtime
WORKDIR /app
ENV NODE_ENV=production

# Prisma query engine requires libssl at runtime
RUN apt-get update -y && apt-get install -y openssl && rm -rf /var/lib/apt/lists/*

# Copy dependencies from deps stage (includes generated Prisma client),
# then prune dev dependencies to keep the image smaller.
COPY package.json package-lock.json ./
COPY --from=deps /app/node_modules ./node_modules
RUN npm prune --omit=dev

COPY --from=build /app/dist ./dist

# Health endpoint defaults to PORT=3000, but PORT is configured via env.
EXPOSE 3000

CMD ["node", "dist/index.js"]
