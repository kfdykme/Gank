package xyz.kfdykme.gank;
import xyz.kfdykme.gank.base.BaseView;
import xyz.kfdykme.gank.base.BasePresenter;
import java.util.List;
import xyz.kfdykme.gank.bean.*;


public class ArtcleListContract
{
	public interface View extends BaseView<ArtcleListPresenter>{
		void addViews(List<DataEntity.result> list_result);
		void initLoadViews(Boolean isInited);
		Boolean isInited();
		
	}
	public interface Presenter extends BasePresenter {
		void loadArticle(String type, int page);
		
	}
}
