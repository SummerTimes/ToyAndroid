##使用简要说明

###楼层的创建

1.通用楼层要写在这个lib里面并且在FloorViewType类中定义类型；
2.每个楼层创建由三部：

（1）Factory 提供viewHolder的创建

（2）创建楼层参考title包下的image
    
####Combine的使用

1.设立初衷：主要是方便楼层的顺序控制以及楼层之间逻辑的隔离；

2.使用方法：
   
   （1）楼层管理类：DefaultEasyFloorManager主要负责楼层位置的计算
   （2）所写Combine需要继承AbstractFloorCombine
        combine类似于MVP结构中的Presenter层，负责UI和数据的交互是逻辑层
        holder为UI层，dataHolder为Model层，
        combine尽量聚合独立，combine需要交互可以通过每个模块创建的Presenter进行
        交互，不要直接引用传递。
    例：
    public class BannerCombine extends AbstractFloorCombine implements IBannerFloorEvent {
    
        private final int mIndex;
    
        public BannerCombine(int key) {
            this(key, -1);
        }
    
        public BannerCombine(int key, int index) {
            super(key);
            this.mIndex = index;
        }
    
        @Override
        protected void onUIReady(final IUI ui, boolean alreadyInsert) {
            if (!alreadyInsert) {//这里判断是因为避免重复监听导致逻辑混乱
                Logger.i("==========banner===========");
                PreLoader.listenData(getKey(), new GroupedDataListener<ResourceEntity>() {
                    @Override
                    public String keyInGroup() {
                        return RecommendSalesDataLoader.KEY_IN_GROUP;
                    }
    
                    @Override
                    public void onDataArrived(ResourceEntity value) {
                        getFloors().clear();
                        if (value == null) {
                            return;
                        }
                        Logger.i("==========banner资源位返回===========");
                        BannerEntity bannerEntity = new BannerEntity();
                        ResourceEntity.OtherResourceBean recommendResource = getRecommendResource(value.getOtherResource());
                        if (recommendResource == null) {
                            return;
                        }
                        bannerEntity.setBannerResource(recommendResource);
                        Floor<BannerEntity> bannerEntityFloor = Floor.buildFloor(FloorViewType.VIEW_TYPE_BANNER, bannerEntity);
                        bannerEntityFloor.setUiEvent(BannerCombine.this);
    
                        add(bannerEntityFloor);
                        ui.onCombineRequestInflateUI(BannerCombine.this);
                    }
                });
            }
        }
    
    
        private @Nullable
        ResourceEntity.OtherResourceBean getRecommendResource(List<ResourceEntity.OtherResourceBean> otherResource) {
            if (otherResource == null || otherResource.size() == 0) {
                return null;
            }
            for (ResourceEntity.OtherResourceBean otherResourceBean : otherResource) {
    
                if (TextUtils.equals("7011", otherResourceBean.getResourceId())) {
                    return otherResourceBean;
                }
            }
            return null;
        }
    
        @Override
        public int getIndex() {
            return mIndex;
        }
    
        @Override
        public void onBannerClick(int position, BannerEntity data) {
        }
    }
    
  （3）便于减少冗余代码添加AbstractPresenter
       该类实现了INetManager接口，提供刷新以及当页面请求recyclerview绘制时onUIReady的封装、、
       
       provideCombinesProvider 方法需要返回非空的页面combine集合
    
