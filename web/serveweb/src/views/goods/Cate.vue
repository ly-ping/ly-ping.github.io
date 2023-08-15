<template>
    <div>
        <!--面包屑导航-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/dashboard' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/goods/category' }">商品管理</el-breadcrumb-item>
            <el-breadcrumb-item>商品分类</el-breadcrumb-item>
        </el-breadcrumb>

        <!-- 卡片视图 -->
        <el-row>
            <el-row>
                <el-button type="primary" @click="showAddCateDialog()">添加分类</el-button>
            </el-row>
        </el-row>
        <!-- 表格区域 -->

        <!-- 树形组件 -->
        <tree-table  class="treeTable"
                     :data="catelist"
                    :columns="columns"
                    :selection-type="false"
                    :expand-type="false"
                    :show-index="true"
                    :border="true">
            <!-- 排序 -->
            <template slot="catelevel" slot-scope="scope">
                <el-tag size="mini" v-if="scope.row.catelevel == 1">一级</el-tag>
                <el-tag type="success" size="mini" v-else-if="scope.row.catelevel == 2">二级</el-tag>
                <el-tag type="warning" size="mini" v-else>三级</el-tag>
            </template>
            <!-- 操作列 -->
            <template slot="opt" slot-scope="scope">
                <el-button type="primary" icon="el-icon-edit" size="mini" @click="edit_cate(scope.row)">编辑</el-button>
                <el-button type="danger" icon="el-icon-delete" size="mini" @click="delete_cate(scope.row)">删除</el-button>
            </template>
        </tree-table>



        <!--添加分类信息和编辑分类信息公用一个对话框-->
        <el-dialog
                class="Categorycard"
                title="分类信息"
                :visible.sync="addCateDialogVisible"
                width="50%"
                @close="addCateDialogClosed()">
            <!-- 添加分类表单 -->
            <el-form :model="addCateForm" :rules="addCateFormRules"
                     ref="addCateFormRef" label-width="100px">
                <el-form-item label="分类名称" prop="catename">
                    <el-input v-model="addCateForm.catename"></el-input>
                </el-form-item>
                <el-form-item label="父级分类">
                    <!--级联选择器-->
                    <el-cascader
                            v-model="selectedKeys"
                            :options="catelist"
                            :props="cascaderProps"
                            @change="parentCateChanged()"
                            clearable
                            placeholder="不选默认一级分类">
                    </el-cascader>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addCateDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="addCate()">确 定</el-button>
            </span>
        </el-dialog>

    </div>

</template>

<script>
    // 直接树
    function getChildrenCategories(categories, categoryId = 0) {
        const result = [];
        for (let i = 0; i < categories.length; i++) {
            const category = categories[i];
            if (category.catepid == categoryId) {
                const children = getChildrenCategories(categories, category.id);
                category.children = children;
                result.push(category);
            }
        }
        return result;
    }

    export default {
        name: "Cate",

        data(){
            return {
                //查询条件
                query:{

                },
                //商品分类数据，默认为空
                catelist: [],


                //添加分类的表单数据对象
                addCateForm: {
                    //将要添加的分类的名称
                    catename: '',
                    //父级分类的ID
                    catepid: '',
                    //父级分类的名称
                    catepname: '',
                    //当前分类等级，默认要添加的是1级分类
                    catelevel: 1
                },
               //添加分类表单的验证规则对象
                addCateFormRules: {
                    catename: [
                        { required: true, message: '请输入分类名称', trigger: 'blur' }
                    ]
                },
                //控制是否显示添加分类的对话框
                addCateDialogVisible: false,
                //指定级联选择器的配置对象
                cascaderProps: {
                    value: 'id',
                    label: 'catename',
                    children: 'children',
                    leaf: 'catelevel',
                    expandTrigger: 'hover', //悬浮显示
                    checkStrictly: true //可以选择任一级选项
                },
                //级联选择器选中的父级分类的ID
                selectedKeys :[],

                //绑定各列的数据
                columns: [
                    {
                        label: '分类名称',
                        prop: 'catename',
                        minWidth: '100px',
                    },
                    // {
                    //     label: '分类级别',
                    //     prop: 'catelevel',
                    //     minWidth: '50px',
                    // },
                    // {
                    //     label: '父分类',
                    //     prop: 'catepid',
                    // },
                    {
                        label: '排序',
                        //表示将当前列定义为模板列
                        type: 'template',
                        //表示当前这一列使用模板名称
                        template: 'catelevel'
                    },
                    {
                        label: '操作',
                        //表示将当前列定义为模板列
                        type: 'template',
                        //表示当前这一列使用模板名称
                        template: 'opt'
                    }
                ]

            }
        },
        mounted(){
            this.getCategory();
        },
        methods:{
            //获取商品分类数据
            getCategory(){
                this.axios.get('http://localhost:8902/cate/getall').then((resp)=>{
                    console.log('resp.data',JSON.parse(JSON.stringify(resp.data)));
                    //扁平化的数据转变为多层数据
                    let data = resp.data;
                    this.catelist = getChildrenCategories(data);
                    console.log('this.catelist', this.catelist);
                });
            },
            //编辑分类
            edit_cate(row){
                this.addCateForm = row;
                console.log('edit',this.addCateForm);
                this.addCateDialogVisible = true;
            },
            //删除分类
            delete_cate(scope){
                //弹框询问用户是否删除数据
                this.$confirm('此操作将永久删除该参数, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let id = scope.id;
                    this.axios.delete("http://localhost:8902/cate/delete/"+id).then((resp)=>{
                        if(resp.data.success){
                            // 如果删除成功，则重新加载分类列表数据，并显示成功提示框
                            this.getCategory();//重新加载数据
                            this.$message({
                                type: 'success',
                                message: '删除参数成功!',
                                center: true
                            });
                        }else {
                            // 如果删除失败，则显示失败提示框
                            this.$message.error('删除参数失败！');
                        }
                    })
                }).catch(() => {
                    // 如果删除失败，则显示失败提示框
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },


            //添加分类，点击按钮，展示添加分类的对话框
            showAddCateDialog(){
                this.addCateForm = {};
                //先触发这个方法，当未选择父级分类的时候就会默认一级分类，否则不选择父级分类该方法就不触发，只能先选择再clear才能添加一级分类
                this.parentCateChanged();
                this.addCateDialogVisible = true;
            },

            //点击按钮，添加新的分类
            async addCate(){
                console.log('addform',this.addCateForm);
                try {
                    const { data } = await this.axios.post('http://localhost:8902/cate/save', this.addCateForm);
                    // 上面一句相当于下面的语段，更加简洁
                    // this.axios.post('http://localhost:8902/cate/save', this.addCateForm).then(resp => {
                    //     const data = resp.data;
                    //     // ...
                    // }).catch(error => {
                    //     console.error(error);
                    // });
                    if (data.success) {
                        this.$message.success('添加分类成功！');
                        this.getCategory(); // 刷新分类列表
                        this.addCateDialogVisible = false; // 关闭对话框
                    }else{
                        this.$message.error('添加分类失败！');
                    }
                } catch (error) {
                    console.error(error);
                    this.$message.error('添加分类失败！');
                }
            },
            //监听对话框的关闭时间，重置表单数据
            addCateDialogClosed(){
                //拿到表单的引用，调用重置方法
                this.$refs.addCateFormRef.resetFields();
                //清除级联选择器中选择的数据
                this.selectedKeys = [];
                //将表单的父级ID和分类级别重置
                this.addCateForm.catepid = 0;
                this.addCateForm.catelevel = 1;
            },
            //选择项发生变化触发这个函数
            parentCateChanged(){
                console.log('this.selectedKeys',this.selectedKeys);
                console.log('addCateForm', typeof this.addCateForm, this.addCateForm);
                //如果 selectKeys数组的length大于0，证明选中了父级分类
                //如果 selectKeys的length小于0，证明没有选择父级分类
                if(this.selectedKeys.length>0){
                    //获取父级分类的ID
                    this.addCateForm.catepid =  this.selectedKeys[this.selectedKeys.length-1];
                    //为当前分类的等级赋值,要加一，不然显示的和父分类同级
                    this.addCateForm.catelevel = this.selectedKeys.length+1;
                    // return;
                }else{
                    //没有选择父级分类，则添加一级分类，此时层级重置为1，父级id为0
                    //获取父级分类的ID
                    this.addCateForm.catepid =  0;
                    //为当前分类的等级赋值
                    this.addCateForm.catelevel = 1;
                }

            },

        }

    }
</script>

<style lang="scss" scoped>

    .treeTable{
        margin-top: 15px;
        margin-bottom: 15px;
    }
    el-cascader{
        max-height: 200px;
    }
    .Categorycard{
        /*width: 25%;*/
        display: inline-block;
        /*margin-right: 5%;*/
        justify-content: center;
        .el-form-item {
            display: flex;
            justify-content:center;
            /* 以下为可选的调整样式 */
            margin-bottom: 20px;
            /*width: 400px;*/
            .el-input,.el-cascader{
                width: 300px;
            }
        }
    }
</style>