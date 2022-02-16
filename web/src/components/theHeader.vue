<template>
  <a-layout-header class="header">
    <a-menu
        theme="dark"
        mode="horizontal"
        v-model:selectedKeys="selectedKeys1"
        :style="{ lineHeight: '64px' }"
    >
      <a-menu-item key="/">
        <router-link to="/">Home</router-link>
      </a-menu-item>
      <a-menu-item key="/admin/books">
        <router-link to="/admin/books">Manage Ebooks</router-link>
      </a-menu-item>
      <a-menu-item key="/about">
        <router-link to="/about">About</router-link>
      </a-menu-item>
    </a-menu>
  </a-layout-header>
</template>

<script lang="ts">
import {defineComponent, ref} from 'vue';

export default defineComponent({
  name: 'the-header',
  setup() {
    return {
      selectedKeys1: ref<string[]>(['home']),
    };
  }
});
</script>