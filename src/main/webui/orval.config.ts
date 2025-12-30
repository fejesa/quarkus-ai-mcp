import { defineConfig } from 'orval';

export default defineConfig({
  catalog: {
    /** The path of the generated OpenAPI scheme */
    input: 'api/message-template.yaml',
    output: {
      /** Generates the scheme and client implementation in different files */
      mode: 'single',
      /** Path to the file which will contain the client implementation. */
      target: 'src/app/api/service/message-template.ts',
      /** Type of the client that we use */
      client: 'angular',
      /** Base url of the API; it is generated to the client implementation. It should be a configuration value. */
      baseUrl: 'http://localhost:8080'
    },
    hooks: {
      afterAllFilesWrite: 'prettier --write',
    },
  },
});
