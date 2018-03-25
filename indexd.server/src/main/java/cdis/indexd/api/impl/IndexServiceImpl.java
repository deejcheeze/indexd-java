package cdis.indexd.api.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import cdis.indexd.api.IndexService;
import cdis.indexd.model.FileIndex;
import cdis.indexd.values.Document;
import cdis.indexd.values.IndexParams;
import nw.orm.core.query.QueryParameter;
import nw.orm.jpa.JDao;
import nw.orm.jpa.JpaDaoFactory;

public class IndexServiceImpl implements IndexService {
	
	@Inject
	private JpaDaoFactory daoFactory;
	private JDao<FileIndex> indexDao;
	
	@PostConstruct
	public void init() {
		indexDao = daoFactory.getDao(FileIndex.class);
	}

	@Override
	public Document get(String indexId) {
		FileIndex index = indexDao.find(QueryParameter.create("did", indexId));
		return Document.fromIndex(index);
	}

	@Override
	public IndexParams get(int limit, int size, int start, String[] urls, String[] hashes, String fileName,
			String version, String[] metadata) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Document add(Document index) {
		FileIndex fileIndex = index.toIndex();
		if(fileIndex.getDid() != null) {
			// check if did is unique
			return index;
		}
		throw new WebApplicationException("Document ID already in use", Status.CONFLICT);
	}

	@Override
	public void addRevision(String indexId, Document index) {
		// TODO Auto-generated method stub

	}

	@Override
	public void listRevisions(String indexId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getLatestRevisions(String indexId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(String indexId) {
		// TODO Auto-generated method stub

	}

	@Override
	public Response delete(String indexId, String revision) {
		// TODO Auto-generated method stub
		return null;
	}

}
