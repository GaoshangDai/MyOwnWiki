<template>
  <a-layout>
    <the-header class="header"></the-header>
    <router-view/>
    <the-footer class="footer"></the-footer>
  </a-layout>
</template>

<script lang="ts">
import {defineComponent, ref} from 'vue'
import theHeader from '@/components/theHeader.vue'
import theFooter from "@/components/theFooter.vue"

export default defineComponent({
  components: {
    theHeader,
    theFooter,
  },
  setup() {
    return {
      collapsed: ref<boolean>(false),
    }
  },
})
</script>

<style>
.ant-layout-header.header {
  padding: 0 25px;
}

.ant-layout-footer.footer {
  padding: 10px 50px;
}

.footer {
  position: fixed;
  width: 100%;
  bottom: 0;
}
</style>
