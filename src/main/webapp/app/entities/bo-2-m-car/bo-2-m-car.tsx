import React from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities, reset } from './bo-2-m-car.reducer';
import { IBo2mCar } from 'app/shared/model/bo-2-m-car.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IBo2mCarProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IBo2mCarState extends IPaginationBaseState {
  search: string;
}

export class Bo2mCar extends React.Component<IBo2mCarProps, IBo2mCarState> {
  state: IBo2mCarState = {
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
    const { bo2mCarList, match } = this.props;
    return (
      <div>
        <h2 id="bo-2-m-car-heading">
          <Translate contentKey="sampleappApp.bo2mCar.home.title">Bo 2 M Cars</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="sampleappApp.bo2mCar.home.createLabel">Create a new Bo 2 M Car</Translate>
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
                    placeholder={translate('sampleappApp.bo2mCar.home.search')}
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
            {bo2mCarList && bo2mCarList.length > 0 ? (
              <Table responsive aria-describedby="bo-2-m-car-heading">
                <thead>
                  <tr>
                    <th className="hand" onClick={this.sort('id')}>
                      <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('name')}>
                      <Translate contentKey="sampleappApp.bo2mCar.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('createdAt')}>
                      <Translate contentKey="sampleappApp.bo2mCar.createdAt">Created At</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th>
                      <Translate contentKey="sampleappApp.bo2mCar.bo2mOwner">Bo 2 M Owner</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th />
                  </tr>
                </thead>
                <tbody>
                  {bo2mCarList.map((bo2mCar, i) => (
                    <tr key={`entity-${i}`}>
                      <td>
                        <Button tag={Link} to={`${match.url}/${bo2mCar.id}`} color="link" size="sm">
                          {bo2mCar.id}
                        </Button>
                      </td>
                      <td>{bo2mCar.name}</td>
                      <td>
                        <TextFormat type="date" value={bo2mCar.createdAt} format={APP_DATE_FORMAT} />
                      </td>
                      <td>{bo2mCar.bo2mOwner ? <Link to={`bo-2-m-owner/${bo2mCar.bo2mOwner.id}`}>{bo2mCar.bo2mOwner.id}</Link> : ''}</td>
                      <td className="text-right">
                        <div className="btn-group flex-btn-group-container">
                          <Button tag={Link} to={`${match.url}/${bo2mCar.id}`} color="info" size="sm">
                            <FontAwesomeIcon icon="eye" />{' '}
                            <span className="d-none d-md-inline">
                              <Translate contentKey="entity.action.view">View</Translate>
                            </span>
                          </Button>
                          <Button tag={Link} to={`${match.url}/${bo2mCar.id}/edit`} color="primary" size="sm">
                            <FontAwesomeIcon icon="pencil-alt" />{' '}
                            <span className="d-none d-md-inline">
                              <Translate contentKey="entity.action.edit">Edit</Translate>
                            </span>
                          </Button>
                          <Button tag={Link} to={`${match.url}/${bo2mCar.id}/delete`} color="danger" size="sm">
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
                <Translate contentKey="sampleappApp.bo2mCar.home.notFound">No Bo 2 M Cars found</Translate>
              </div>
            )}
          </InfiniteScroll>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ bo2mCar }: IRootState) => ({
  bo2mCarList: bo2mCar.entities,
  totalItems: bo2mCar.totalItems,
  links: bo2mCar.links,
  entity: bo2mCar.entity,
  updateSuccess: bo2mCar.updateSuccess
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
)(Bo2mCar);
