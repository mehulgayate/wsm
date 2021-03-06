package com.wsm.entity.support;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.wsm.entity.Cluster;
import com.wsm.entity.GraphData;
import com.wsm.entity.GraphData.GraphType;
import com.wsm.entity.Report;

@Transactional
public class Repository {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<String> getClusterStrings(){
		return getSession().createQuery("select distinct klStringValue from "+Report.class.getName())
				.list();
	}

	public List<Report> listAllReports(){
		return getSession().createQuery("from "+Report.class.getName())
				.list();
	}

	public Cluster findClusterByName(String name){
		return (Cluster) getSession().createQuery("FROM "+Cluster.class.getName()+" where name=:name")
				.setParameter("name", name)
				.uniqueResult();
	}

	public List<Cluster> listAllClusters(){
		return getSession().createQuery("FROM "+Cluster.class.getName())
				.list();
	}

	public void removeAllRecords(){
		getSession().createQuery("delete FROM "+Report.class.getName())
		.executeUpdate();
	}

	Session getSession(){
		return sessionFactory.getCurrentSession();	
	}

	public GraphData findGraphData(GraphType graphType){

		return (GraphData) getSession().createQuery("FROM "+GraphData.class.getName()+" where graphType=:graphType")
				.setParameter("graphType", graphType)
				.uniqueResult();
	}
	public List<GraphData> listGraphData(GraphType graphType){

		return  getSession().createQuery("FROM "+GraphData.class.getName()+" where graphType=:graphType")
				.setParameter("graphType", graphType)
				.list();
	}
	
	public Long findCountGraphData(GraphType graphType){

		return (Long) getSession().createQuery("select count(*) FROM "+GraphData.class.getName()+" where graphType=:graphType")
				.setParameter("graphType", graphType)
				.uniqueResult();
	}
}
