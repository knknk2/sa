import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './o-2-o-car.reducer';
import { IO2oCar } from 'app/shared/model/o-2-o-car.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IO2oCarDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class O2oCarDetail extends React.Component<IO2oCarDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { o2oCarEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="sampleappApp.o2oCar.detail.title">O2oCar</Translate> [<b>{o2oCarEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="sampleappApp.o2oCar.name">Name</Translate>
              </span>
            </dt>
            <dd>{o2oCarEntity.name}</dd>
            <dt>
              <Translate contentKey="sampleappApp.o2oCar.o2oDriver">O 2 O Driver</Translate>
            </dt>
            <dd>{o2oCarEntity.o2oDriver ? o2oCarEntity.o2oDriver.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/o-2-o-car" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/o-2-o-car/${o2oCarEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ o2oCar }: IRootState) => ({
  o2oCarEntity: o2oCar.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(O2oCarDetail);
