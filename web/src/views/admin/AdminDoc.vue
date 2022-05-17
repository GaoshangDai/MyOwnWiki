<template>
  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <a-row :gutter="24">
        <a-col :span="8">
          <p>
            <a-form
                layout="inline"
                :model="param"
            >
              <a-form-item>
                <a-button type="primary" @click="add()">
                  New
                </a-button>
              </a-form-item>
            </a-form>
          </p>
          <a-table
              v-if="level1.length > 0"
              :columns="columns"
              :row-key="record => record.id"
              :data-source="level1"
              :loading="loading"
              :pagination="false"
              size="small"
              :defaultExpandAllRows="true"
          >
            <template #name="{ text, record }">
              {{record.sort}} {{text}}
            </template>
            <template v-slot:action="{ text, record }">
              <a-space size="small">
                <a-button type="primary" @click="edit(record)" size="small">
                  Edit
                </a-button>
                <a-popconfirm
                    title="Are you sure delete this item?"
                    ok-text="Yes"
                    cancel-text="No"
                    @confirm="handleDelete(record.id)"
                >
                  <a-button type="danger" size="small">
                    Delete
                  </a-button>
                </a-popconfirm>
              </a-space>
            </template>
          </a-table>
        </a-col>
        <a-col :span="16">
          <p>
            <a-form layout="inline" :model="param">
              <a-form-item>
                <a-button type="primary" @click="handleSave">
                  Save
                </a-button>
              </a-form-item>
            </a-form>
          </p>
          <a-form :model="doc" layout="vertical">
            <a-form-item>
              <a-input v-model:value="doc.name" placeholder="Name"/>
            </a-form-item>
            <a-form-item>
              <a-tree-select
                  v-model:value="doc.parent"
                  style="width: 100%"
                  :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
                  :tree-data="treeSelectData"
                  placeholder="Please select"
                  tree-default-expand-all
                  :replaceFields="{title: 'name', key: 'id', value: 'id'}"
              >
              </a-tree-select>
            </a-form-item>
            <a-form-item>
              <a-input v-model:value="doc.sort" placeholder="Order"/>
            </a-form-item>
            <a-form-item>
              <div id="content"></div>
            </a-form-item>
          </a-form>
        </a-col>
      </a-row>

    </a-layout-content>
  </a-layout>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue'
import axios from 'axios'
import { message } from "ant-design-vue"
import { Tool } from "../../../util/tool"
import {useRoute} from "vue-router"
import E from 'wangeditor'

export default defineComponent({
  name: 'AdminDoc',
  setup() {
    const route = useRoute()
    const param = ref()
    param.value = {}
    const docs = ref()
    const loading = ref(false)

    const columns = [
      {
        title: 'Name',
        dataIndex: 'name',
        slots: {customRender: 'name'}
      },
      {
        key: 'action',
        slots: {customRender: 'action'}
      }
    ]

    const level1 = ref()
    level1.value = []

    const handleQuery = () => {
      loading.value = true
      level1.value = []
      axios.get("/doc/all").then((response) => {
        loading.value = false
        const data = response.data
        if (data.success) {
          docs.value = data.content
          level1.value = []
          level1.value = Tool.array2Tree(docs.value, 0)
        } else {
          message.error(data.message)
        }
      })
    }

    const doc = ref()
    doc.value = {}
    const modalVisible = ref(false)
    const modalLoading = ref(false)
    const treeSelectData = ref()
    treeSelectData.value = []
    const editor = new E('#content')
    editor.config.zIndex = 0

    const handleSave = () => {
      modalLoading.value = true
      doc.value.content = editor.txt.html()
      axios.post("/doc/save", doc.value).then((response) => {
        modalLoading.value = false
        const data = response.data
        if (data.success) {
          modalVisible.value = false
          handleQuery()
        } else {
          message.error(data.message)
        }
      })
    }

    const setDisable = (treeSelectData: any, id: any) => {
      for (let i = 0; i < treeSelectData.length; i++) {
        const node = treeSelectData[i]
        if (node.id === id) {
          console.log("disabled", node)
          node.disabled = true

          const children = node.children
          if (Tool.isNotEmpty(children)) {
            for (let j = 0; j < children.length; j++) {
              setDisable(children, children[j].id)
            }
          }
        } else {
          const children = node.children
          if (Tool.isNotEmpty(children)) {
            setDisable(children, id)
          }
        }
      }
    }

    const edit = (record: any) => {
      modalVisible.value = true
      doc.value = Tool.copy(record)
      treeSelectData.value = Tool.copy(level1.value)
      setDisable(treeSelectData.value, record.id)
      treeSelectData.value.unshift({id: 0, name: 'None'})
    }

    const add = () => {
      modalVisible.value = true
      doc.value = {
        ebookId: route.query.ebookid
      }
      treeSelectData.value = Tool.copy(level1.value)
      treeSelectData.value.unshift({id: 0, name: 'None'})
    }

    const ids: Array<string>[] = []
    const getDeleteIds = (treeSelectData: any, id: any) => {
      for (let i = 0; i < treeSelectData.length; i++) {
        const node = treeSelectData[i]
        if (node.id === id) {
          ids.push(id)
          const children = node.children
          if (Tool.isNotEmpty(children)) {
            for (let j = 0; j < children.length; j++) {
              getDeleteIds(children, children[j].id)
            }
          }
        } else {
          const children = node.children
          if (Tool.isNotEmpty(children)) {
            getDeleteIds(children, id)
          }
        }
      }
    }

    const handleDelete = (id: number) => {
      ids.length = 0
      getDeleteIds(level1.value, id)
      axios.delete("/doc/delete/" + ids.join(",")).then((response) => {
        const data = response.data
        if (data.success) {
          handleQuery()
        }
      })
    }

    onMounted(() => {
      handleQuery()
      editor.create()
    })

    return {
      param,
      level1,
      columns,
      loading,
      edit,
      add,
      handleDelete,
      modalVisible,
      modalLoading,
      handleSave,
      handleQuery,
      doc,
      treeSelectData
    }
  }
})
</script>

<style scoped>
img {
  width: 50px;
  height: 50px;
}
</style>