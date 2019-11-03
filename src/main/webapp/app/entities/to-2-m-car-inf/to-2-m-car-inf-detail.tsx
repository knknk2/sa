import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './to-2-m-car-inf.reducer';
import { ITo2mCarInf } from 'app/shared/model/to-2-m-car-inf.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITo2mCarInfDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class To2mCarInfDetail extends React.Component<ITo2mCarInfDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { to2mCarInfEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="sampleappApp.to2mCarInf.detail.title">To2mCarInf</Translate> [<b>{to2mCarInfEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="sampleappApp.to2mCarInf.name">Name</Translate>
              </span>
            </dt>
            <dd>{to2mCarInfEntity.name}</dd>
            <dt>
              <Translate contentKey="sampleappApp.to2mCarInf.to2mOwnerInf">To 2 M Owner Inf</Translate>
            </dt>
            <dd>{to2mCarInfEntity.to2mOwnerInfId ? to2mCarInfEntity.to2mOwnerInfId : ''}</dd>
            <dt>
              <Translate contentKey="sampleappApp.to2mCarInf.to2mDriverInf">To 2 M Driver Inf</Translate>
            </dt>
            <dd>{to2mCarInfEntity.to2mDriverInfId ? to2mCarInfEntity.to2mDriverInfId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/to-2-m-car-inf" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/to-2-m-car-inf/${to2mCarInfEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ to2mCarInf }: IRootState) => ({
  to2mCarInfEntity: to2mCarInf.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(To2mCarInfDetail);
