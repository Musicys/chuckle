import js from '@eslint/js';
import globals from 'globals';
import vueEslintParser from 'vue-eslint-parser';
import vue from 'eslint-plugin-vue';
import typescriptEslint from '@typescript-eslint/eslint-plugin';
import typescriptParser from '@typescript-eslint/parser';
import prettierPlugin from 'eslint-plugin-prettier';

export default [
   {
      ignores: ['node_modules/**', 'dist/**', '*.d.ts', '**/*.d.ts']
   },
   {
      files: ['**/*.vue'],
      languageOptions: {
         ecmaVersion: 'latest',
         globals: {
            ...globals.browser,
            ...globals.node,
            ...globals.es2021
         },
         parser: vueEslintParser,
         parserOptions: {
            parser: typescriptParser,
            sourceType: 'module'
         }
      },
      plugins: {
         vue,
         prettier: prettierPlugin
      },
      rules: {
         // Prettier rules
         'prettier/prettier': 'error',

         // Vue rules
         'vue/multi-word-component-names': 'off',
         'vue/no-unused-vars': 'warn',
         'vue/no-unused-components': 'warn',
         'vue/require-default-prop': 'off',
         'vue/require-prop-types': 'off',

         // JavaScript rules
         semi: ['error', 'always'],
         quotes: ['error', 'single'],
         'comma-dangle': ['error', 'never']
      }
   },
   {
      files: ['**/*.{js,jsx}'],
      languageOptions: {
         ecmaVersion: 'latest',
         globals: {
            ...globals.browser,
            ...globals.node,
            ...globals.es2021
         }
      },
      plugins: {
         prettier: prettierPlugin
      },
      rules: {
         'prettier/prettier': 'error',
         semi: ['error', 'always'],
         quotes: ['error', 'single'],
         'comma-dangle': ['error', 'never']
      }
   },
   {
      files: ['**/*.{ts,tsx}'],
      languageOptions: {
         ecmaVersion: 'latest',
         globals: {
            ...globals.browser,
            ...globals.node,
            ...globals.es2021
         },
         parser: typescriptParser,
         parserOptions: {
            sourceType: 'module'
         }
      },
      plugins: {
         '@typescript-eslint': typescriptEslint,
         prettier: prettierPlugin
      },
      rules: {
         'prettier/prettier': 'error',
         '@typescript-eslint/no-explicit-any': 'off',
         '@typescript-eslint/no-unused-vars': 'warn',
         semi: ['error', 'always'],
         quotes: ['error', 'single'],
         'comma-dangle': ['error', 'never']
      }
   }
];
