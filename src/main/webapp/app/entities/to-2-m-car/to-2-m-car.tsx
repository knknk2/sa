import React from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction, getSortState, IPaginationBaseState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities, reset } from './to-2-m-car.reducer';
import { ITo2mCar } from 'app/shared/model/to-2-m-car.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface ITo2mCarProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface ITo2mCarState extends IPaginationBaseState {
  search: string;
}

export class To2mCar extends React.Component<ITo2mCarProps, ITo2mCarState> {
  state: ITo2mCarState = {
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
    const { to2mCarList, match } = this.props;
    return (
      <div>
        <h2 id="to-2-m-car-heading">
          <Translate contentKey="sampleappApp.to2mCar.home.title">To 2 M Cars</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="sampleappApp.to2mCar.home.createLabel">Create a new To 2 M Car</Translate>
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
                    placeholder={translate('sampleappApp.to2mCar.home.search')}
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
            {to2mCarList && to2mCarList.length > 0 ? (
              <Table responsive aria-describedby="to-2-m-car-heading">
                <thead>
                  <tr>
                    <th className="hand" onClick={this.sort('id')}>
                      <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('name')}>
                      <Translate contentKey="sampleappApp.to2mCar.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th>
                      <Translate contentKey="sampleappApp.to2mCar.to2mOwner">To 2 M Owner</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th>
                      <Translate contentKey="sampleappApp.to2mCar.to2mDriver">To 2 M Driver</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th />
                  </tr>
                </thead>
                <tbody>
                  {to2mCarList.map((to2mCar, i) => (
                    <tr key={`entity-${i}`}>
                      <td>
                        <Button tag={Link} to={`${match.url}/${to2mCar.id}`} color="link" size="sm">
                          {to2mCar.id}
                        </Button>
                      </td>
                      <td>{to2mCar.name}</td>
                      <td>{to2mCar.to2mOwnerId ? <Link to={`to-2-m-person/${to2mCar.to2mOwnerId}`}>{to2mCar.to2mOwnerId}</Link> : ''}</td>
                      <td>
                        {to2mCar.to2mDriverId ? <Link to={`to-2-m-person/${to2mCar.to2mDriverId}`}>{to2mCar.to2mDriverId}</Link> : ''}
                      </td>
                      <td className="text-right">
                        <div className="btn-group flex-btn-group-container">
                          <Button tag={Link} to={`${match.url}/${to2mCar.id}`} color="info" size="sm">
                            <FontAwesomeIcon icon="eye" />{' '}
                            <span className="d-none d-md-inline">
                              <Translate contentKey="entity.action.view">View</Translate>
                            </span>
                          </Button>
                          <Button tag={Link} to={`${match.url}/${to2mCar.id}/edit`} color="primary" size="sm">
                            <FontAwesomeIcon icon="pencil-alt" />{' '}
                            <span className="d-none d-md-inline">
                              <Translate contentKey="entity.action.edit">Edit</Translate>
                            </span>
                          </Button>
                          <Button tag={Link} to={`${match.url}/${to2mCar.id}/delete`} color="danger" size="sm">
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
                <Translate contentKey="sampleappApp.to2mCar.home.notFound">No To 2 M Cars found</Translate>
              </div>
            )}
          </InfiniteScroll>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ to2mCar }: IRootState) => ({
  to2mCarList: to2mCar.entities,
  totalItems: to2mCar.totalItems,
  links: to2mCar.links,
  entity: to2mCar.entity,
  updateSuccess: to2mCar.updateSuccess
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
)(To2mCar);
