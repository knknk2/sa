import React from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import {
  openFile,
  byteSize,
  Translate,
  translate,
  ICrudSearchAction,
  ICrudGetAllAction,
  TextFormat,
  getSortState,
  IPaginationBaseState
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities, reset } from './fields.reducer';
import { IFields } from 'app/shared/model/fields.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IFieldsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IFieldsState extends IPaginationBaseState {
  search: string;
}

export class Fields extends React.Component<IFieldsProps, IFieldsState> {
  state: IFieldsState = {
    search: '',
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.reset();
  }

  componentDidUpdate() {
    if (this.props.updateSuccess) {
      this.reset();
    }
  }

  search = () => {
    if (this.state.search) {
      this.props.reset();
      this.setState({ activePage: 1 }, () => {
        const { activePage, itemsPerPage, sort, order, search } = this.state;
        this.props.getSearchEntities(search, activePage - 1, itemsPerPage, `${sort},${order}`);
      });
    }
  };

  clear = () => {
    this.props.reset();
    this.setState({ search: '', activePage: 1 }, () => {
      this.props.getEntities();
    });
  };

  handleSearch = event => this.setState({ search: event.target.value });

  reset = () => {
    this.props.reset();
    this.setState({ activePage: 1 }, () => {
      this.getEntities();
    });
  };

  handleLoadMore = () => {
    if (window.pageYOffset > 0) {
      this.setState({ activePage: this.state.activePage + 1 }, () => this.getEntities());
    }
  };

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => {
        this.reset();
      }
    );
  };

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order, search } = this.state;
    if (search) {
      this.props.getSearchEntities(search, activePage - 1, itemsPerPage, `${sort},${order}`);
    } else {
      this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
    }
  };

  render() {
    const { fieldsList, match } = this.props;
    return (
      <div>
        <h2 id="fields-heading">
          <Translate contentKey="sampleappApp.fields.home.title">Fields</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="sampleappApp.fields.home.createLabel">Create a new Fields</Translate>
          </Link>
        </h2>
        <Row>
          <Col sm="12">
            <AvForm onSubmit={this.search}>
              <AvGroup>
                <InputGroup>
                  <AvInput
                    type="text"
                    name="search"
                    value={this.state.search}
                    onChange={this.handleSearch}
                    placeholder={translate('sampleappApp.fields.home.search')}
                  />
                  <Button className="input-group-addon">
                    <FontAwesomeIcon icon="search" />
                  </Button>
                  <Button type="reset" className="input-group-addon" onClick={this.clear}>
                    <FontAwesomeIcon icon="trash" />
                  </Button>
                </InputGroup>
              </AvGroup>
            </AvForm>
          </Col>
        </Row>
        <div className="table-responsive">
          <InfiniteScroll
            pageStart={this.state.activePage}
            loadMore={this.handleLoadMore}
            hasMore={this.state.activePage - 1 < this.props.links.next}
            loader={<div className="loader">Loading ...</div>}
            threshold={0}
            initialLoad={false}
          >
            {fieldsList && fieldsList.length > 0 ? (
              <Table responsive aria-describedby="fields-heading">
                <thead>
                  <tr>
                    <th className="hand" onClick={this.sort('id')}>
                      <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('str')}>
                      <Translate contentKey="sampleappApp.fields.str">Str</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('num1')}>
                      <Translate contentKey="sampleappApp.fields.num1">Num 1</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('num2')}>
                      <Translate contentKey="sampleappApp.fields.num2">Num 2</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('num3')}>
                      <Translate contentKey="sampleappApp.fields.num3">Num 3</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('num4')}>
                      <Translate contentKey="sampleappApp.fields.num4">Num 4</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('num5')}>
                      <Translate contentKey="sampleappApp.fields.num5">Num 5</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('date1')}>
                      <Translate contentKey="sampleappApp.fields.date1">Date 1</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('date2')}>
                      <Translate contentKey="sampleappApp.fields.date2">Date 2</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('date3')}>
                      <Translate contentKey="sampleappApp.fields.date3">Date 3</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('date4')}>
                      <Translate contentKey="sampleappApp.fields.date4">Date 4</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('uuid')}>
                      <Translate contentKey="sampleappApp.fields.uuid">Uuid</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('bool')}>
                      <Translate contentKey="sampleappApp.fields.bool">Bool</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('enumeration')}>
                      <Translate contentKey="sampleappApp.fields.enumeration">Enumeration</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('blob')}>
                      <Translate contentKey="sampleappApp.fields.blob">Blob</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('blob2')}>
                      <Translate contentKey="sampleappApp.fields.blob2">Blob 2</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('blob3')}>
                      <Translate contentKey="sampleappApp.fields.blob3">Blob 3</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th />
                  </tr>
                </thead>
                <tbody>
                  {fieldsList.map((fields, i) => (
                    <tr key={`entity-${i}`}>
                      <td>
                        <Button tag={Link} to={`${match.url}/${fields.id}`} color="link" size="sm">
                          {fields.id}
                        </Button>
                      </td>
                      <td>{fields.str}</td>
                      <td>{fields.num1}</td>
                      <td>{fields.num2}</td>
                      <td>{fields.num3}</td>
                      <td>{fields.num4}</td>
                      <td>{fields.num5}</td>
                      <td>
                        <TextFormat type="date" value={fields.date1} format={APP_LOCAL_DATE_FORMAT} />
                      </td>
                      <td>
                        <TextFormat type="date" value={fields.date2} format={APP_DATE_FORMAT} />
                      </td>
                      <td>
                        <TextFormat type="date" value={fields.date3} format={APP_DATE_FORMAT} />
                      </td>
                      <td>{fields.date4}</td>
                      <td>{fields.uuid}</td>
                      <td>{fields.bool ? 'true' : 'false'}</td>
                      <td>
                        <Translate contentKey={`sampleappApp.Enum1.${fields.enumeration}`} />
                      </td>
                      <td>
                        {fields.blob ? (
                          <div>
                            <a onClick={openFile(fields.blobContentType, fields.blob)}>
                              <img src={`data:${fields.blobContentType};base64,${fields.blob}`} style={{ maxHeight: '30px' }} />
                              &nbsp;
                            </a>
                            <span>
                              {fields.blobContentType}, {byteSize(fields.blob)}
                            </span>
                          </div>
                        ) : null}
                      </td>
                      <td>
                        {fields.blob2 ? (
                          <div>
                            <a onClick={openFile(fields.blob2ContentType, fields.blob2)}>
                              <Translate contentKey="entity.action.open">Open</Translate>
                              &nbsp;
                            </a>
                            <span>
                              {fields.blob2ContentType}, {byteSize(fields.blob2)}
                            </span>
                          </div>
                        ) : null}
                      </td>
                      <td>{fields.blob3}</td>
                      <td className="text-right">
                        <div className="btn-group flex-btn-group-container">
                          <Button tag={Link} to={`${match.url}/${fields.id}`} color="info" size="sm">
                            <FontAwesomeIcon icon="eye" />{' '}
                            <span className="d-none d-md-inline">
                              <Translate contentKey="entity.action.view">View</Translate>
                            </span>
                          </Button>
                          <Button tag={Link} to={`${match.url}/${fields.id}/edit`} color="primary" size="sm">
                            <FontAwesomeIcon icon="pencil-alt" />{' '}
                            <span className="d-none d-md-inline">
                              <Translate contentKey="entity.action.edit">Edit</Translate>
                            </span>
                          </Button>
                          <Button tag={Link} to={`${match.url}/${fields.id}/delete`} color="danger" size="sm">
                            <FontAwesomeIcon icon="trash" />{' '}
                            <span className="d-none d-md-inline">
                              <Translate contentKey="entity.action.delete">Delete</Translate>
                            </span>
                          </Button>
                        </div>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            ) : (
              <div className="alert alert-warning">
                <Translate contentKey="sampleappApp.fields.home.notFound">No Fields found</Translate>
              </div>
            )}
          </InfiniteScroll>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ fields }: IRootState) => ({
  fieldsList: fields.entities,
  totalItems: fields.totalItems,
  links: fields.links,
  entity: fields.entity,
  updateSuccess: fields.updateSuccess
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Fields);
